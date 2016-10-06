/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.examples.controllers;

import edu.eci.pdsw.examples.beans.impl.CalculadorBasicoCuentas;
import edu.eci.pdsw.examples.model.ExcepcionManejadorOrdenes;
import edu.eci.pdsw.examples.model.Orden;
import edu.eci.pdsw.examples.model.Plato;
import edu.eci.pdsw.examples.services.ManejadorOrdenes;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/ordenes")
public class CuentaResourceController {

    @Autowired
    ManejadorOrdenes mo = null;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoXX() {
        try {
            List<Orden> data = mo.getOrdenes();
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(CuentaResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/platos", method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetPlatoXX() {
        try {
            List<Plato> data = mo.getPlatos();
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(CuentaResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/{idorden}", method = RequestMethod.GET)
    public ResponseEntity<?> buscarRecursoXX(@PathVariable("idorden") String id) {
        try {
            //   List<Orden> data = mo.getOrdenes();
            Orden consultarOrden = mo.consultarOrden(Integer.parseInt(id));
            return new ResponseEntity<>(consultarOrden, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(CuentaResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.NOT_FOUND);
        }
    }
   @RequestMapping(value = "/{idorden}/add", method = RequestMethod.PUT)
   public synchronized @ResponseBody ResponseEntity<?> putProduct(@PathVariable("idorden")String id, @RequestBody Plato product){
       try{         
        /*String[] split = product.split(",");
        Plato plat = new Plato(split[0],Integer.parseInt(split[1]));*/  
        boolean okk = true;
        while(okk){
            if(mo.getOk().get()){
                Orden ordentmp = mo.consultarOrden(Integer.parseInt(id));
                ordentmp.agregarPlato(product);
                okk = false;
            }
            
        }
        
        
        return new ResponseEntity<>("Plato agregado correctamente",HttpStatus.ACCEPTED);
       }catch(ExcepcionManejadorOrdenes e){
           return new ResponseEntity<>("Erorrrrr",HttpStatus.ACCEPTED);
           
       }
       }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> manejadorPostRecursoXX(@RequestBody Orden od) {
        try {
            mo.registrarOrden(od);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(CuentaResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.FORBIDDEN);
        }

    }
    
    @RequestMapping(value = "/{idorden}/total", method = RequestMethod.GET)
    public synchronized ResponseEntity<?> calcularTotalXX(@PathVariable("idorden") String id) {
        try {

            //   List<Orden> data = mo.getOrdenes();
            int calcularTotalOrden = mo.calcularTotalOrden(Integer.parseInt(id));
            int respuesta =calcularTotalOrden;
            return new ResponseEntity<>(respuesta, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            Logger.getLogger(CuentaResourceController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.NOT_FOUND);
        }
    }

}
