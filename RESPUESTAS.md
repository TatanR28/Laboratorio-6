# Laboratorio 6 — Respuestas y guía resuelta

Todas las salidas de este documento fueron comprobadas ejecutando los programas con Java 21.


---

## Diagnóstico inicial (respuestas correctas, para comparar al final)

1. ¿Qué diferencia hay entre JDK y JVM?
El JDK sirve para crear programas en Java y la JVM sirve para ejecutarlos.

2. ¿Qué produce el compilador javac?
Convierte el código Java en archivos .class para que la máquina virtual pueda ejecutarlos.

3. ¿Qué ocurre cuando un método llama a otro método?
Se ejecuta el otro método y, cuando termina, el programa vuelve al método donde estaba antes.

4. Si dos variables apuntan al mismo objeto y una modifica su estado, ¿qué observará la otra?
También verá los cambios, porque las dos variables apuntan al mismo objeto.

5. ¿Java pasa los objetos «por referencia»? Escribe tu explicación inicial.
Yo creo que Java pasa una copia de la referencia del objeto, por eso las dos variables pueden modificar el mismo objeto, pero si una cambia a otro objeto, la otra sigue apuntando al original.


---

1. **JDK vs JVM:** el JDK es el kit de desarrollo (compilador `javac`, `java`, `javap`, etc.) e incluye una JVM. La JVM es la máquina virtual que ejecuta el bytecode y administra la memoria.
2. **`javac` produce** archivos `.class` con bytecode.
3. **Un método llama a otro:** se crea un marco (stack frame) nuevo en la pila con sus parámetros y variables locales. El método que llamó queda en espera hasta que el nuevo retorne.
4. **Dos variables al mismo objeto:** la otra ve el cambio, porque ambas llevan a la misma instancia.
5. **¿"Por referencia"?** No. Java pasa todos los argumentos por valor. Cuando el argumento es un objeto, lo que se copia es el valor de la referencia.

---

## Práctica 6.1 — JDK, JRE y JVM

Versiones comprobadas en mi equipo: `java 21.0.12.1`, `javac 21.0.12.1`, `javap 21.0.12.1`.

| Herramienta | Qué hace |
|---|---|
| `javac` | Compila `.java` y genera `.class` |
| `java` | Inicia un programa ya compilado |
| `javap` | Muestra el bytecode de un `.class` |

**Ejercicio "Herramienta correcta"**

1. Para saber si puedes compilar: `javac --version`.
2. Para iniciar un programa: `java`.
3. Tener `java` no garantiza `javac` porque `java` viene también en el JRE, pero el compilador solo viene en el JDK. En tu equipo, con Java 8, funcionaba `java` pero `javac` no se reconocía.
4. No. Un `.class` ya está compilado. Basta una JVM compatible, más sus dependencias si las tiene.
5. `javap`.

**Reto "Explica el error"**

La JVM solo ejecuta bytecode ya compilado. Para desarrollar y compilar se necesita el JDK, que incluye el compilador `javac` y además trae una JVM. Instalar solo una JVM permite ejecutar programas, no crearlos desde su código fuente.

---


**Ejercicio "¿Por qué no cambió la salida?"**

- Cambiar `bonus` a 8 sin recompilar: la salida sigue siendo `Total: 12`, porque la JVM ejecuta el `.class`, que se generó con el código anterior.
- Después de `javac -d out ExecutionFlow.java` y volver a ejecutar: `Total: 15`.
- Explicación: el `.java` es solo el código fuente. Lo que se ejecuta es el `.class`, y este solo cambia cuando se vuelve a compilar.

**Laboratorio de errores**

| Error | Qué ocurre | Etapa |
|---|---|---|
| 1. Quitar `;` de `int base = 7;` | `javac` informa `';' expected`; no se genera `.class` | Compilación |
| 2. `java -cp out MissingClass` | `Could not find or load main class MissingClass` (`ClassNotFoundException`). Lo busca el lanzador/cargador de clases de la JVM | Inicio |
| 3. `java -cp out ExecutionFlow.class` | Falla porque `java` espera el nombre de la clase, no del archivo. Interpreta el punto como separador de paquetes y busca una clase `class` dentro del paquete `ExecutionFlow` | Inicio |
| 4. `first - second` | Compila y ejecuta, pero imprime `Total: 2` en lugar de 12 | Lógica |

**Comprobación:** `.java` = código fuente; `.class` = bytecode; `javac` = compilador; `java` = lanzador; `-cp` = dónde buscar clases; `javap` = desensamblador; bytecode = instrucciones para la JVM; JIT = compilación a código máquina en tiempo de ejecución de partes del programa.

---

## Práctica 6.3 — Stack

`MethodStack` imprime `Final result: 11`. Recorrido: `main` entrega 4, `doubleValue` retorna 8, `calculate` devuelve 8 + 3 = 11.

**Cuando `doubleValue` está activo:**

1. `number` vale 4.
2. Esperan el retorno `calculate` y `main`.
3. `doubleValue` devuelve 8.
4. En `result` se guarda 11.

Pila en ese momento (arriba es lo activo):

```
[ doubleValue ]  number = 4
[ calculate   ]  value = 4, doubled = (pendiente)
[ main        ]  initialValue = 4, result = (pendiente)
```

**Pregunta de análisis:** No, `calculate` no ha terminado. Está esperando el valor que retorne `doubleValue` para poder asignarlo a `doubled` y seguir.

**SafeRecursion** con `countDown(3)` imprime:

```
Entering: 3
Entering: 2
Entering: 1
Entering: 0
Base case
Returning: 1
Returning: 2
Returning: 3
```

Con `countDown(2)`: `Entering: 2`, `Entering: 1`, `Entering: 0`, `Base case`, `Returning: 1`, `Returning: 2`. En el caso base hay 3 invocaciones activas más `main`: `countDown(0)`, `countDown(1)`, `countDown(2)` y `main`.

**Depuración:** con `return value + 3;` el resultado es 7 en vez de 11. `doubled` se calcula (vale 8), pero nunca se usa en el `return`. Valor calculado no es lo mismo que valor retornado.

---

## Práctica 6.4 — Heap

`ObjectReferences` imprime:

```
First and alias: true
First and independent: false
First name: Sol
Independent name: Luna
```

Dibujo:

```
first       ----> Objeto A: Pet("Luna")  -> luego "Sol"
alias       ----> Objeto A
independent ----> Objeto B: Pet("Luna")
```

`Pet alias = first;` no crea otro objeto: copia la referencia.

**String:** `firstText == secondText` es `false` (objetos distintos) y `.equals()` es `true` (mismo contenido).

**Arreglos:** imprime `99`, `2`, `true`, `false`. `sameArray` es alias de `firstNumbers`; `otherArray` es otro arreglo.

**`null`:** no crea ningún objeto. Invocar un método sobre `null` produce `NullPointerException` en ejecución. Compila porque el compilador solo comprueba tipos, no el valor que habrá en la variable.

**GC:** al hacer `current = new Pet("Sol")`, el primer `Pet("Luna")` queda elegible para GC si nada más lo referencia. No se garantiza cuándo se recolecta.

**Ejercicio "Reasignar un alias":**

- `third = first` → `third.getName()` es `Sol`.
- `alias = new Pet("Nova")` → `alias` apunta ahora a un objeto nuevo C.
- `alias.rename("Max")` → cambia C, no A.
- Resultado: `first=Sol third=Sol independent=Luna alias=Max`.
- A no cambia porque `alias` ya no apunta a A.

**Depuración (null):** el error es `NullPointerException`; el stack trace señala la línea de `missing.getName()`. Corrección: `if (missing != null) { ... }`.

---

## Práctica 6.5 — Paso de parámetros y PetMatch

- `PrimitiveParameters`: `Inside: 15` y `Outside: 5`. Se modificó la copia (`value`), no `quantity`.
- `ArrayReferences`: `Inside: 7` y `Outside: 99, 2`.
  - `updateFirst` modifica el mismo arreglo (por la referencia compartida).
  - `replaceArray` solo cambia su parámetro local; `numbers` conserva la referencia al arreglo original.
  - Al final hay dos arreglos: el original y el nuevo.
- `MemoryDemo`:

```
Dentro: Otra
Fuera: Sol
```

No se lanza el `AssertionError`.

**Paso a paso:**

1. `original` → objeto A `Pet("Luna")`.
2. `local` recibe una copia del valor de referencia de `original`. Ambos llegan a A.
3. `local.name = "Sol"` modifica A. `original` lo ve.
4. `local = new Pet("Otra")` crea B. Solo cambia `local`; `original` sigue en A.
5. `Dentro:` consulta B (`Otra`); `Fuera:` consulta A (`Sol`).
6. Al retornar, `local` deja de existir. B queda elegible para GC.

**Variantes:**

| Variante | Salida | Explicación |
|---|---|---|
| A (reasignar antes) | `Dentro: Sol` / `Fuera: Luna` | `local.name = "Sol"` modifica B. A no cambia. Además el `AssertionError` se lanzaría, porque `original.name` sería `Luna` |
| B (sin reasignación) | `Dentro: Sol` / `Fuera: Sol` | Ambas impresiones consultan A |
| C (retornar objeto) | el llamador asigna `c = createReplacement()` | Al retornar un valor, el llamador elige reasignar su propia variable |
| D (alias) | `alias.name` es `Sol` | Declarar `alias = original` no crea objetos; es la misma instancia |

**Ejercicio `value = null`:** la variable del llamador no queda en `null`. Reasignar el parámetro cambia solo la copia local de la referencia; modificar el estado de un objeto sí es visible para todos los que lo referencian.

**Depuración de la afirmación:** «Como el método modificó el objeto original, Java pasa objetos por referencia» es incorrecta. Java pasa una copia del valor de referencia: el parámetro local apunta al mismo objeto y por eso puede modificarlo, pero si se hace una reasignación del parámetro, la variable del llamador no cambia.

---

## Práctica 6.6 — Reto integrador

**Predicción:**

1. Se ejecutan 3 `new PetProfile(...)`: Luna, Nova y Max.
2. Se crean 3 objetos: A = Luna, B = Nova, C = Max.
3. Sí, `pet` y `alias` apuntan a A.
4. El primer incremento lo recibe A (Luna).
5. El segundo lo recibe B (Nova).
6. No, `createReplacement(pet)` no cambia a qué apunta `pet`.
7. Salida:

```
Inside: Nova (1)
Outside: Luna (1)
Same reference: true
Replacement: Max (0)
```

8. No. Al terminar `update`, `current` desaparece y `main` no tiene referencia a Nova.
9. B (Nova) es elegible para GC. A sigue referenciado por `pet` y `alias`; C por `replacement`.

`MemoryChallengeCheck` imprime `All reference checks passed`.

**Tres errores:**

- **A:** si `independent = original`, falla `Distinct objects share an identity`, porque `original == independent` pasa a ser `true`.
- **B:** `createReplacement(pet);` sin asignar el retorno: `pet` no cambia y el objeto `Max` queda sin referencias (elegible para GC).
- **C:** `missing.registerVisit()` con `missing = null` produce `NullPointerException`. Se corrige con `if (missing != null)`.

**Reto de ampliación (`ExtensionChallenge`):**

- `registerTwoVisits` cambia el **estado de un objeto** (el mismo que recibe).
- `createNewProfile` entrega un nuevo **valor de referencia** para que el llamador lo asigne.

**Microejercicio:** imprime `9` y `true`. `other` es alias de `values`, así que el cambio mediante `other[1]` se ve en `values[1]`.

---

## Diagnóstico final (15 preguntas)

1. El JDK contiene el compilador `javac` (y `javap`, etc.).
2. La JVM carga las clases, ejecuta el bytecode (interpretando o con JIT) y administra la memoria.
3. `javac` compila `.java` a `.class`; `java` inicia el programa compilado.
4. `javap -c` muestra el bytecode (instrucciones de la JVM) de una clase.
5. Un stack frame es el marco que se crea por cada invocación de método, con sus parámetros, variables locales y pila de operandos.
6. La pila de llamadas es la secuencia de marcos de los métodos activos; la pila de operandos está dentro de cada marco y se usa para operar valores.
7. En el Heap.
8. `int` guarda un valor primitivo; `PetProfile` guarda una referencia a una instancia.
9. `first == second` compara si ambas referencias identifican la misma instancia (o ambas son `null`).
10. Porque ambas variables llevan al mismo objeto.
11. Porque el parámetro es una copia local de la referencia; reasignarlo no toca la variable del llamador.
12. `Dentro: Otra` y `Fuera: Sol`, por la modificación del objeto A y la reasignación local a B.
13. Que ningún camino de referencias alcanzables llega a él, así que el GC puede recuperar su memoria (sin garantía de cuándo).
14. Porque invocar un método sobre `null` no tiene objeto al que llegar: `NullPointerException` en ejecución.
15. Con un breakpoint y el panel *Call Stack*: muestra qué método está activo y cuáles esperan; con *Step Out* se ve el retorno.

---
