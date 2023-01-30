# NOTAS- Cabify Mobile Challenge

La idea de esta nota es presentar algunas de las ideas que intente plasmar en el challenge. Con la
idea de que lo fundamental es el codigo limpio y entendiendo que la capa de presentacion se puede
iterar, el foco se puso en tratar de utilizar las mejores practicas posibles.

Con un diseño Basado en Domain Driven Design (DDD), Interaction Driven Development (IDD), Clean Code
y Arquitectura limpia el desarrollo fue hecho completamente mediante Test Driven Development (TDD),
donde se puede encontrar la logica de negocio en el core. Para abstraer las capas de la
arquitectura, las interfaces fueron fundamentales.

Las clases que no fueron testeadas son las que involucran a colaboradores externos, por ejemplo
Retrofit y la capa de presentacion que involucra a colaboradores de Android.

De modo que los descuentos sean extensibles con la menor inclusion de codigo posible, la estrategia
utilizada fue la utilizacion de reglas bansandonos en uno de los principios de SOLID, OPEN-CLOSED.
De esta forma, si los productos involucrados aplican a las reglas, dependiendo de la promocion que
se quiera aplicar, se modificaran los precios.