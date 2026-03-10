package com.impulse_spring_app.lock;

import com.impulse_spring_app.dto.CompraIngressoDTO;
import com.impulse_spring_app.model.Evento;
import com.impulse_spring_app.model.Participante;
import com.impulse_spring_app.repository.EventoRepository;
import com.impulse_spring_app.repository.ParticipanteRepository;
import com.impulse_spring_app.repository.VendaIngressoRepository;
import com.impulse_spring_app.service.VendaIngressoService;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.time.LocalDate;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventoOptimisticLockTest {

    @Autowired
    private VendaIngressoService vendaIngressoService;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private VendaIngressoRepository vendaIngressoRepository;

    private Evento evento;
    private Participante p1;
    private Participante p2;

    @BeforeEach
    void setUp() {
        vendaIngressoRepository.deleteAll();
        participanteRepository.deleteAll();
        eventoRepository.deleteAll();

        // Evento com capacidade 1
        evento = new Evento();
        evento.setNome("Evento Teste Lock");
        evento.setLocal("Auditório");
        evento.setData(LocalDate.now().plusDays(1));
        evento.setCapacidade(1);
        eventoRepository.saveAndFlush(evento);

        // Participantes
        p1 = new Participante();
        p1.setNome("Participante 1");
        p1.setEmail("p1@email.com");
        participanteRepository.saveAndFlush(p1);

        p2 = new Participante();
        p2.setNome("Participante 2");
        p2.setEmail("p2@email.com");
        participanteRepository.saveAndFlush(p2);
    }

    @Test
    void deveLancarErroLockOtimistaEmCompraConcorrente() throws InterruptedException, ExecutionException {
        CompraIngressoDTO compra1 = new CompraIngressoDTO(p1.getId(), evento.getId());
        CompraIngressoDTO compra2 = new CompraIngressoDTO(p2.getId(), evento.getId());

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<String> task1 = () -> {
            try {
                vendaIngressoService.comprarIngresso(compra1);
                return "Comprou P1";
            } catch (ObjectOptimisticLockingFailureException e) {
                return "Erro Lock P1";
            }
        };

        Callable<String> task2 = () -> {
            try {
                Thread.sleep(5); // delay para simular concorrência
                vendaIngressoService.comprarIngresso(compra2);
                return "Comprou P2";
            } catch (ObjectOptimisticLockingFailureException e) {
                return "Erro Lock P2";
            }
        };

        Future<String> f1 = executor.submit(task1);
        Future<String> f2 = executor.submit(task2);

        String r1 = f1.get();
        String r2 = f2.get();

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Resultado 1: " + r1);
        System.out.println("Resultado 2: " + r2);

        // Apenas 1 ingresso deve ter sido vendido
        assertEquals(1, vendaIngressoRepository.findAll().size());

        // Pelo menos uma thread deve ter lançado ObjectOptimisticLockingFailureException
        boolean erroLock = r1.contains("Erro Lock") || r2.contains("Erro Lock");
        assertTrue(erroLock);
    }
}
