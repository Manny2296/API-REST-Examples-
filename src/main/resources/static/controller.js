

//========//
//Datos de prueba usados para probar la interfaz mientras no haya
//conexión con el servidor
var p1 = new Plato("papas", 1000);
var p2 = new Plato("gaseosa", 500);

var o = new Orden();
o.agregarPlato(p1);
var o2 = new Orden();
o2.agregarPlato(p2);
//========//

//la orden actualmente seleccionada
ordenActual = new Orden();
plat = new Plato("", 0);

//identificador de la orden actualente seleccionada
idOrdenActual = 0;

//ordenes actualmente disponibles
ordenesDisponibles = [];
cuenta = "";
//platos actualmente disponibles
platos = [];
platosdisp = [];
ordenes = [];


/**
 * Actualiza el conjunto de ordenes disponibles mostradas, consultando las mismas
 * en el servidor. La actualización se realiza de manera asíncrona con AJAX.
 * 
 */

actualizarOrdenesDisponibles = function () {
    return $.get("http://localhost:8080/ordenes").then(
            function (data) {
                ordenes = JSON.parse(JSON.stringify(data));
                $("#ordenes").children().remove();
                for (var i = 0; i < ordenes.length; i++) {
                    $("#ordenes").append("<option value='" + i + "' selected='selected'>" +"Orden "+ i + "</option>");
                }              
            },
            function () {
                alert("$.get failed!");
            });
          
};

/**
 * Agrega un nuevo plato a la orden actual, la actualiza en el servidor, y 
 * recalcula el precio.
 * Se debe tener en cuenta que el precio NO se podrá calcular hasta que
 * se haya hecho la actualización (para esto use 'promesas' encadenadas).
 * es dado por el servidor).
 * @param {type} p
 * @returns {undefined}
 */

// AGREGAR PLATO EN PRUEBAS FALTA ARREGLAR PLATOS DISPONIBLES PARA PROBAR ESTO
agregarPlato = function (p) {
    var temp;
    for (var i = 0; i < platosdisp.length; i++) {
        if (p.valueOf() === platosdisp[i].nombre) {
            temp = platosdisp[i];         
        }
    }    
    $("#tablaplatos").append("<tr><td>" + temp.nombre + "</td> <td>" + temp.precio + "</td></tr>");
    $.ajax({
        url: "http://localhost:8080/ordenes/" + idOrdenActual + "/add",
        type: 'PUT',
        data: JSON.stringify(temp),
        contentType: "application/json",
        
    }).then(calcularCuenta);
    
    


};


calcularCuenta = function () {
    //alert("entra a calcular");
    $.get("http://localhost:8080/ordenes/" + idOrdenActual + "/total").then(
            function (data) {

                cuenta = 0;
                console.info(JSON.stringify(data));

                cuenta = JSON.stringify(data);
                $("#resumenCuenta").children().remove();
                $("#resumenCuenta").append("<h1>Total:" + cuenta + "</h1>");
            });
};



/**
 * Cambia la orden actualmente seleccionada
 * @param {type} id
 * @returns {undefined}
 */
cambiarOrdenActual = function (id) {

    idOrdenActual = id;
    $.get("http://localhost:8080/ordenes/" + idOrdenActual).then(
            function (data) {
                ordenActual = JSON.parse(JSON.stringify(data));
                //actualizar la presentación
                $("#tablaplatos").children().remove();
                $("#tablaplatos").append("<tr><th>Producto</th><th>Precio</th></tr>");
                for (var i = 0; i < ordenActual.platos.length; i++) {
                    plat = ordenActual.platos[i];

                    $("#tablaplatos").append("<tr><td>" + plat.nombre + "</td> <td>" + plat.precio + "</td></tr>");

                }
                calcularCuenta();
            });




};




/**
 * Actualiza el listado de los platos disponibles
 * @returns {undefined}
 */


actualizarPlatosDisponibles = function () {
    return $.get("http://localhost:8080/ordenes/platos").then(
            function (data) {
                var cont = 0;
                platosdisp = JSON.parse(JSON.stringify(data));
                for (var i = 0; i < platosdisp.length; i++) {
                    cont++;
                    platos[platosdisp[i].nombre] = platosdisp[i];
                    $("#platos").append(" <li id='" + platosdisp[i].nombre + "' class='draggable'>" +"<p>"+ platosdisp[i].nombre +"</p>"+"<p>"+platosdisp[i].precio+"</p></li> ");
                }
                $("li").draggable({
                    helper: 'clone'
                });
            },
            function () {
                alert("$.get failed!");
            });
};

/**
 * Operaciones que se realizarán automáticamente una vez el DOM haya sido
 * cargado por completo.
 * 
 */
$(document).ready(
        function () {
            actualizarPlatosDisponibles();
            $("#contenido").droppable({
                drop: function (event, ui) {
                    var idElementoSoltado = ui.draggable.attr("id");
                    agregarPlato(idElementoSoltado);
                }
            });


        }
);

