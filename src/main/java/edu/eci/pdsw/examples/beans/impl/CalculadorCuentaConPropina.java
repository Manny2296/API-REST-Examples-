package edu.eci.pdsw.examples.beans.impl;

import edu.eci.pdsw.examples.model.Orden;
import edu.eci.pdsw.examples.beans.CalculadorCuenta;
import edu.eci.pdsw.examples.model.Plato;
import java.util.List;
import org.springframework.stereotype.Service;


public class CalculadorCuentaConPropina implements CalculadorCuenta {

    @Override
    public int calcularCosto(Orden o) {
        int cuenta = 0;
        List<Plato> platos = o.getPlatos();
        for (Plato plato : platos) {
            cuenta += plato.getPrecio();
        }
        return cuenta+2000;
    }

}
