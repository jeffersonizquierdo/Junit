package org.jefferson.junit5app.ejemplos.models;

import org.jefferson.junit5app.ejemplos.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;

public class Cuenta {
    private String persona;
    private BigDecimal saldo;
    private Banco banco;


    public Cuenta(String persona, BigDecimal saldo) {
        this.saldo = saldo;
        this.persona = persona;
    }

    public void setPersona(String persona) {
        this.persona = persona;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getPersona() {
        return persona;
    }
    public BigDecimal getSaldo() {return saldo;}

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public void debido(BigDecimal monto){

        BigDecimal nuevoSaldo = this.saldo.subtract(monto);
        if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0){
            throw new DineroInsuficienteException("Dinero Insuficiente");
        }
        this.saldo = nuevoSaldo;
    }

    public void credito (BigDecimal monto){

        this.saldo= this.saldo.add(monto);
    }

    @Override
    public boolean equals(Object obj) {

        Cuenta c = (Cuenta) obj;

        if (!(obj  instanceof Cuenta)) return false;

        if (this.persona == null || this.saldo == null) return  false;

        return this.persona.equals(c.getPersona()) && this.saldo.equals(c.getSaldo());
    }
}
