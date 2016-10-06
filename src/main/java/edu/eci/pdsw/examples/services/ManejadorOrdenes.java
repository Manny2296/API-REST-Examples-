package edu.eci.pdsw.examples.services;

import java.util.LinkedList;
import java.util.List;

import edu.eci.pdsw.examples.beans.CalculadorCuenta;
import edu.eci.pdsw.examples.beans.CalculadorCuenta;
import edu.eci.pdsw.examples.model.ExcepcionManejadorOrdenes;
import edu.eci.pdsw.examples.model.Orden;
import edu.eci.pdsw.examples.model.Plato;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManejadorOrdenes {

    @Autowired
    CalculadorCuenta calc = null;
    public AtomicBoolean ok = new AtomicBoolean(true);
    List<Plato> platos;
    List<Orden> ordenes;


	public ManejadorOrdenes(){
		ordenes=new LinkedList<>();
                platos = new LinkedList<>();
                cargarOrdenes(this);
	}

        public void setCalculadorCuenta(CalculadorCuenta calc){
            this.calc = calc;
        }
        
        public List<Plato> getPlatos(){
            return platos;
        }
    
        public void registrarPLato (Plato p){
            platos.add(p);
        }
        
        
                      
        public List<Orden> getOrdenes() {
            return ordenes;
        }
	
	public void registrarOrden(Orden o){
		ordenes.add(o);
	}

    public AtomicBoolean getOk() {
        return ok;
    }

    public void setOk(AtomicBoolean ok) {
        this.ok = ok;
    }

        
        
        
	public Orden consultarOrden(int n) throws ExcepcionManejadorOrdenes{
		if (n>=ordenes.size()) throw new ExcepcionManejadorOrdenes("Orden inexistente:");
		return ordenes.get(n);
	}
	
	public int calcularTotalOrden(int n) throws ExcepcionManejadorOrdenes{
		if (n>=ordenes.size()) throw new ExcepcionManejadorOrdenes("Orden inexistente:");
                ok.set(false);
		int total = calc.calcularCosto(ordenes.get(n));	
                ok.set(true);
                return 	total;
	}
	
        private static void cargarOrdenes(ManejadorOrdenes mo){
                
                mo.registrarPLato(new Plato("pizza",7500));
                mo.registrarPLato(new Plato("gaseosa",3900));
                mo.registrarPLato(new Plato("hamburguesa",8000));
                mo.registrarPLato(new Plato("papas",2500));
                mo.registrarPLato(new Plato("gaseosa litro",4000));
                
		Orden o=new Orden();
		o.agregarPlato(new Plato("pizza",7500));
		o.agregarPlato(new Plato("gaseosa",3900));
		o.agregarPlato(new Plato("hamburguesa",8000));
		o.agregarPlato(new Plato("gaseosa 350",200));
		
		mo.registrarOrden(o);
		
		o=new Orden();
		
		o.agregarPlato(new Plato("pizza",7500));
		o.agregarPlato(new Plato("pizza",7500));
		o.agregarPlato(new Plato("pizza",7500));
		o.agregarPlato(new Plato("gaseosa litro",4000));
		
		mo.registrarOrden(o);
	}
        
}
