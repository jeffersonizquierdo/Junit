package org.jefferson.junit5app.ejemplos;
import org.jefferson.junit5app.ejemplos.exceptions.DineroInsuficienteException;
import org.jefferson.junit5app.ejemplos.models.Banco;
import org.jefferson.junit5app.ejemplos.models.Cuenta;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    @Test
    void testNombreCuenta() {

        Cuenta miCuenta = new Cuenta("Jefferson", new BigDecimal("1000.12345"));
        String esperado = "Jefferson";
        String real = miCuenta.getPersona();
        assertNotNull(real,  () -> "La cuenta no puede ser nula");
        assertEquals(esperado, real,  () -> "El nombre de la cuenta no es el esperado");
        assertTrue(real.equals("Jefferson"));
    }

    @Test
    void testSaldoCuenta() {

        Cuenta cuenta = new Cuenta("Jefferson", new BigDecimal("1000.12345"));
        assertNotNull(cuenta.getSaldo());
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    void testReferenciaCuenta(){

        Cuenta jhon = new Cuenta("Jhon", new BigDecimal("8900.9997"));
        Cuenta jhon2 = new Cuenta("Jhon", new BigDecimal("8900.9997"));
//        assertNotEquals(jhon, jhon2);
        assertEquals(jhon, jhon2);
    }

    @Test
    void testDebitoCuenta(){

        Cuenta jefferson = new Cuenta("Jefferson", new BigDecimal("1000.12345"));
        jefferson.debido(new BigDecimal(100));
        assertNotNull(jefferson.getSaldo());
        assertEquals(900, jefferson.getSaldo().intValue());
        assertEquals("900.12345", jefferson.getSaldo().toPlainString());
    }

    @Test
    void testCreditoCuenta(){

        Cuenta jefferson = new Cuenta("Jefferson", new BigDecimal("1000.12345"));
        jefferson.credito(new BigDecimal(100));
        assertNotNull(jefferson.getSaldo());
        assertEquals(1100, jefferson.getSaldo().intValue());
        assertEquals("1100.12345", jefferson.getSaldo().toPlainString());
    }

    @Test
    void testDineroInsuficienteException() {

        Cuenta miCuenta = new Cuenta("Jefferson", new BigDecimal("1000.12345"));
        Exception exception = assertThrows(DineroInsuficienteException.class, ()->{
            miCuenta.debido(new BigDecimal(1500));
        });
        String actual = exception.getMessage();
        String esperado = "Dinero Insuficiente";
        assertEquals(esperado, actual);
    }

    @Test
    void testTransferirDineroCuetnas() {

        Cuenta cuenta1 = new Cuenta("David", new BigDecimal("2500" ));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));
        Banco banco = new Banco();
        banco.setNombre("Gancolombia");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));
        assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
        assertEquals("3000", cuenta1.getSaldo().toPlainString());
    }

    @Test
    void testRelacionBancoCuetnas() {

        Cuenta cuenta1 = new Cuenta("David", new BigDecimal("2500" ));
        Cuenta cuenta2 = new Cuenta("Andres", new BigDecimal("1500.8989"));
        Banco banco = new Banco();
        banco.addCuenta(cuenta1);
        banco.addCuenta(cuenta2);
        banco.setNombre("Bancolombia");
        banco.transferir(cuenta2, cuenta1, new BigDecimal(500));

        assertAll(()-> {
            assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());

            }, ()-> {
                    assertEquals(2, banco.getCuentas().size());
                },
                ()-> {
                    assertEquals("Bancolombia", cuenta1.getBanco().getNombre());
                },
                ()-> {
                    assertEquals("Andres", banco.getCuentas().stream().filter(c -> c.getPersona().equals("Andres"))
                            .findFirst()
                            .get().getPersona());
                },
                ()-> {
                    assertTrue(banco.getCuentas().stream().filter(c -> c.getPersona().equals("Andres"))
                            .findFirst().isPresent());
                },
                ()-> {
                    assertTrue(banco.getCuentas().stream().anyMatch(c -> c.getPersona().equals("Andres")));
                });









    }
}
























