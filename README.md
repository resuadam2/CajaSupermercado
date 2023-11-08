# CajaSupermercado

Proyecto de ejemplo Android + Java que simula el software de una caja de un supermercado. 

En el proyecto se crea una base de datos local con SQLite y SQLiteOpenHelper y se añaden unos productos de ejemplo a la tabla de productos. 

El usuario al entrar se encuentra con la actividad que contiene los productos que tiene en el carrito (vacío por defecto) y puede moverse a otra actividad para añadir productos al carrito u a otra para pagar y finalizar. En esta actividad principal también se muestra el total de la compra hasta el momento.

En la actividad para añadir elementos al carrito se muestran los productos precargados en la base de datos, los cuales son fijos pero sus cantidades y precios son aleatorios. Desde aquí se pueden añadir al carrito.

Una vez se añaden productos al carrito es posible movernos a la actividad para pagar el carrito y finalizar la compra, esta actividad además nos calcula cuánto dinero hemos de devolver para la cantidad ingresada como pago del cliente y nos divide la vuelta en los billetes y monedas correspondientes (en euros). Al pulsar en pagar nos vacía el carrito. 

Desde la actividad principal también es posible borrar elementos del carrito (los cuales también devuelven el stock a su correspondiente producto) u editar la cantidad de un producto ya seleccionado.

Temas tratados en el ejemplo:

- Listeners
- Lambdas
- SQLite
- SQLiteOpenHelper
- Cursores
- Adaptadores personalizados
- GridLayout
- AlertDialogs