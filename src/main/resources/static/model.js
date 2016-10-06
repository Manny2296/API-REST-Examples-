function Orden() {
    this.platos = [];

}

Orden.prototype.agregarPlato=function(plato){
    this.platos.push(plato);
};


function Plato(nombre,precio){
    this.nombre=nombre;
    this.precio=precio;
}

$(document).ready(
        function () {
                        

        }
);
