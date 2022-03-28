package com.example.fitnessapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Esta clase consiste en el adapter de SQLiteOpenHelper. Una clase que permite gestionar la creación y acciones de bases de datos.
 */
public class MiBaseDatosHelper extends SQLiteOpenHelper {

    // Nombre de tablas
    private static final String TABLE_DEP = "deportista";
    private static final String TABLE_ENT = "entrenador";
    private static final String TABLE_ENTRENADO = "entrenado";
    private static final String TABLE_REALIZA = "realiza";
    private static final String TABLE_EJERCITA = "ejercita";
    private static final String TAG = "";

    //Nombres de columnas de la tabla deportista
    private static final String KEY_DEP_ID = "id";
    private static final String KEY_DEP_PASS = "contrasena";
    private static final String KEY_DEP_NOMBRE = "nombre";
    private static final String KEY_DEP_ESTATURA = "estatura";
    private static final String KEY_DEP_PESO = "peso";
    private static final String KEY_DEP_EDAD = "edad";
    private static final String KEY_DEP_FECHA = "fecha_registro";

    //Nombres de las columnas de la tabla entrenador
    private static final String KEY_ENT_ID = "id";
    private static final String KEY_ENT_COD = "codigo";
    private static final String KEY_ENT_PASS = "contrasena";
    private static final String KEY_ENT_ESP = "especialidad";
    private static final String KEY_ENT_CEN = "centro";
    private static final String KEY_ENT_FECHA = "fecha_registro";

    //Nombres de las columnas de la tabla entrenado
    private static final String KEY_ENTRD_ID = "id";
    private static final String KEY_ENTRD_NOM = "nombre";
    private static final String KEY_ENTRD_EST = "estatura";
    private static final String KEY_ENTRD_OBJ = "objetivo";
    private static final String KEY_ENTRD_ED = "edad";
    private static final String KEY_ENTRD_ID_ENT = "id_entrenador";


    public MiBaseDatosHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //crear tablas
        db.execSQL("CREATE TABLE actividad (id INTEGER PRIMARY KEY, nombre TEXT not null, intensidad text check(intensidad in ('alta','media','baja')) not null default 'media')");
        db.execSQL("CREATE TABLE entrenador (id INTEGER PRIMARY KEY AUTOINCREMENT, codigo integer not null, contrasena text not null, especialidad text, centro text, fecha_registro text not null)");
        db.execSQL("CREATE TABLE entrenado (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre text not null, estatura float, edad integer, objetivo text, id_entrenador integer not null, constraint en_entr foreign key (id_entrenador) references entrenador(id))");
        db.execSQL("CREATE TABLE deportista (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre text not null, contrasena text not null, estatura float, edad integer, peso float, fecha_registro text not null)");
        db.execSQL("CREATE TABLE realiza (id INTEGER PRIMARY KEY AUTOINCREMENT, codigo_actividad integer not null, id_deportista text not null, fecha text not null, tiempo double not null, repeticiones int not null, constraint cod_act foreign key (codigo_actividad) references actividad (codigo) on delete cascade, constraint nom_dep foreign key (id_deportista) references deportista(id) on delete cascade)");
        db.execSQL("CREATE TABLE ejercita (id INTEGER PRIMARY KEY AUTOINCREMENT, codigo_actividad integer not null, id_entrenador integer not null, id_entrenado integer not null, fecha text not null, tiempo int, repeticiones int, constraint cod_act_e foreign key (codigo_actividad) references actividad (codigo) on delete cascade, constraint nom_dep_e foreign key (id_entrenador) references entrenador (id) on delete cascade, constraint cod_ent_e foreign key (id_entrenado) references entrenado (id) on delete cascade)");

        //La información de las actividades debe añadirse al inicio para poder registrar la actividad realizada.
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('1','Abdominales', 'media')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('2','Abuctores', 'media')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('3','Burpee', 'alta')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('4','Flexion', 'alta')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('5','Patada Gluteos', 'baja')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('6','Triceps', 'baja')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('7','Peso muerto', 'media')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('8','Puente', 'media')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('9','Sentadilla', 'media')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('10','Steps', 'baja')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('11','Zancada', 'media')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('12','Zancadas Lateral', 'media')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //eliminar versión anterior de la tabla (si existe)
        db.execSQL("DROP TABLE IF EXISTS IFIT");
        //crear nueva versión
        db.execSQL("CREATE TABLE actividad (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT not null, intensidad text check(intensidad in ('alta','media','baja')) not null default 'media')");
        db.execSQL("CREATE TABLE entrenador (id INTEGER PRIMARY KEY AUTOINCREMENT, codigo integer not null, contrasena text not null, especialidad text, centro text, fecha_registro text not null)");
        db.execSQL("CREATE TABLE entrenado (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre text not null, estatura float, edad integer, objetivo text, id_entrenador integer not null, constraint en_entr foreign key (id_entrenador) references entrenador(id))");
        db.execSQL("CREATE TABLE deportista (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre text not null, contrasena text not null, estatura float, edad integer, peso float, fecha_registro text not null)");
        db.execSQL("CREATE TABLE realiza (id INTEGER PRIMARY KEY AUTOINCREMENT, codigo_actividad integer not null, id_deportista text not null, fecha text not null, tiempo double not null, repeticiones int not null, constraint cod_act foreign key (codigo_actividad) references actividad (codigo) on delete cascade, constraint nom_dep foreign key (id_deportista) references deportista(id) on delete cascade)");
        db.execSQL("CREATE TABLE ejercita (id INTEGER PRIMARY KEY AUTOINCREMENT, codigo_actividad integer not null, id_entrenador integer not null, id_entrenado integer not null, fecha text not null, tiempo int, repeticiones int, constraint cod_act_e foreign key (codigo_actividad) references actividad (codigo) on delete cascade, constraint nom_dep_e foreign key (id_entrenador) references entrenador (id) on delete cascade, constraint cod_ent_e foreign key (id_entrenado) references entrenado (id) on delete cascade)");

        //La información de las actividades debe añadirse al inicio para poder registrar la actividad realizada.
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('1','Abdominales', 'media')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('2','Abuctores', 'media')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('3','Burpee', 'alta')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('4','Flexion', 'alta')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('5','Patada Gluteos', 'baja')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('6','Triceps', 'baja')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('7','Peso muerto', 'media')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('8','Puente', 'media')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('9','Sentadilla', 'media')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('10','Steps', 'baja')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('11','Zancada', 'media')");
        db.execSQL("INSERT INTO ACTIVIDAD VALUES ('12','Zancadas Lateral', 'media')");

    }


    /**
     * método para comprobar el login del usuario deportista
     *
     * @param usuarioTeclado    nombre que el usuario ha introducido
     * @param contrasenaTeclado contraseña que el usuario ha introducido
     * @return true si el login es correcto, false si se produce un error.
     */
    public boolean loginCorrectoUsuario(String usuarioTeclado, String contrasenaTeclado) {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "nombre=? and contrasena=?";
        String[] whereargs = new String[]{usuarioTeclado, contrasenaTeclado};
        Cursor cursor = db.query(
                TABLE_DEP,
                new String[]{"nombre, contrasena"},
                where,
                whereargs,
                null, null, null
        );
        int contador = cursor.getCount();
        cursor.close();
        //si el cursor no está vacío (mayor que 0), el usuario existe, return true.
        return contador > 0;
    }


    /**
     * método para comprobar el login del usuario entrenador
     *
     * @param codigoTeclado     codigo que el usuario ha introducido
     * @param contrasenaTeclado contraseña que el usuario ha introducido
     * @return true si el login es correcto, false si ha fallado.
     */
    public boolean loginCorrectoEntrenador(String codigoTeclado, String contrasenaTeclado) {
        //abrir base de datos
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "codigo=? and contrasena=?";
        String[] whereargs = new String[]{codigoTeclado, contrasenaTeclado};
        Cursor cursor = db.query(
                "entrenador",
                new String[]{"codigo, contrasena"},
                where,
                whereargs,
                null, null, null
        );
        int contador = cursor.getCount();
        cursor.close();
        //si el cursor no está vacío (mayor que 0), el usuario existe, return true.
        return contador > 0;
    }


    /**
     * método para añadir un usuario deportista a la base de datos. Ejecuta una transcacción con la sentencia insert en la tabla deportista.
     *
     * @param nombre     del usuario
     * @param contrasena
     * @param estatura
     * @param peso
     * @param edad
     */
    public void crearDeportista(String nombre, String contrasena, String estatura, String peso, String edad) {
        // Create y/o abrir base de datos en modo escritura
        SQLiteDatabase db = getWritableDatabase();

        // Definir una transacción para mejorar el rendimiento y la consistencia de los datos.
        db.beginTransaction();
        try {
            //obtener fecha actual para registrarla
            Date fechaActual = new java.sql.Date(Calendar.getInstance().getTimeInMillis());

            //crear usuario en la BD
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("contrasena", contrasena);

            //comprobar si los campos opcionales tienen contenido y si el contenido es válido. En caso negativo, no se añaden a la BD.

            //estatura
            values.put("estatura", estatura);

            //peso
            values.put("peso", peso);

            //edad
            values.put("edad", edad);

            //añadir fecha actual como fecha de registro
            values.put("fecha_registro", String.valueOf(fechaActual));

            // insertar en tabla deportista
            db.insertOrThrow(TABLE_DEP, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error al crear el usuario");
        } finally {
            db.endTransaction();
        }
    }

    /**
     * método para obtener los datos de un usuario deportista de la BD mediante su nombre de usuario. Utiliza un cursor y ejecuta una consulta select con todos los campos a la
     * tabla deportista con la condición de que el nombre del usuario coincida con el pasado como parámetro
     *
     * @param nombreUsuario nombre del usuario deportista
     * @return objeto deportista con los datos de la consulta
     */

    public Deportista obtenerDatosDeportista(String nombreUsuario) {
        //objeto deportista para volcar los datos luego.
        Deportista deportistas = new Deportista();

        //consulta: select * from deportista where nombre = "nombre del usuario actual"
        String CONSULTA_DEPORTISTA =
                "SELECT * FROM " + TABLE_DEP + " WHERE " + KEY_DEP_NOMBRE + " = '" + nombreUsuario + "'";

        // "getReadableDatabase()" and "getWriteableDatabase()" retornan el mismo objeto (excepto en casos de poco almacenamiento en disco)
        SQLiteDatabase db = getReadableDatabase();

        //cursor para la consulta
        Cursor cursor = db.rawQuery(CONSULTA_DEPORTISTA, null);

        //iterar el cursor y establecer datos al objeto (solo debe haber un resultado que coincida)
        try {
            if (cursor.moveToFirst()) {
                //establecer valores al nuevo objeto deportista con los que se han encontrado en la BD.
                do {
                    deportistas.id = cursor.getString(cursor.getColumnIndex(KEY_DEP_ID));
                    deportistas.nombre = cursor.getString(cursor.getColumnIndex(KEY_DEP_NOMBRE));
                    deportistas.contrasena = cursor.getString(cursor.getColumnIndex(KEY_DEP_PASS));
                    deportistas.estatura = cursor.getString(cursor.getColumnIndex(KEY_DEP_ESTATURA));
                    deportistas.edad = cursor.getString(cursor.getColumnIndex(KEY_DEP_EDAD));
                    deportistas.peso = cursor.getString(cursor.getColumnIndex(KEY_DEP_PESO));
                    deportistas.registro = cursor.getString(cursor.getColumnIndex(KEY_DEP_FECHA));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error al cargar los datos del deportista de la base de datos");
            //cerrar el cursor al acabar
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        //retornar objeto deportista con los datos de la consulta.
        return deportistas;
    }

    /**
     * método para obtener los datos de un usuario entrenado de la BD mediante el id del usuario. Utiliza un cursor para volcar los resultados de la consulta a un
     * objeto de tipo Entrenado.
     *
     * @param id
     * @return objeto de tipo Entrenado con los datos del usuario.
     */

    public Entrenado obtenerDatosEntrenado(String id) {
        //objeto Entrenado vacío para volcar los datos encontrados.
        Entrenado entrenado = new Entrenado();

        //consulta: select * from deportista where nombre = "nombre del usuario actual"
        String CONSULTA_ENTRENADO =
                "SELECT * FROM " + TABLE_ENTRENADO + " WHERE " + KEY_ENTRD_ID + " = '" + id + "'";

        // "getReadableDatabase()" and "getWriteableDatabase()" retornan el mismo objeto (excepto en casos de poco almacenamiento en disco)
        SQLiteDatabase db = getReadableDatabase();
        //cursor para la consulta
        Cursor cursor = db.rawQuery(CONSULTA_ENTRENADO, null);
        try {
            //iterar el cursor (solo debería tener un elemento)
            if (cursor.moveToFirst()) {
                //establecer valores a las propiedades del objeto.
                do {
                    entrenado.id = cursor.getString(cursor.getColumnIndex(KEY_ENTRD_ID));
                    entrenado.nombre = cursor.getString(cursor.getColumnIndex(KEY_ENTRD_NOM));
                    entrenado.estatura = cursor.getString(cursor.getColumnIndex(KEY_ENTRD_EST));
                    entrenado.edad = cursor.getString(cursor.getColumnIndex(KEY_ENTRD_ED));
                    entrenado.objetivo = cursor.getString(cursor.getColumnIndex(KEY_ENTRD_OBJ));
                    entrenado.idEntrenador = cursor.getString(cursor.getColumnIndex(KEY_ENTRD_ID_ENT));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error al intentar cargar los datos de la base de datos");
            //cerrar el cursor.
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        //devolver objeto Entrenado
        return entrenado;
    }

    /**
     * Método para añadir un usuario entrenador a la base de datos. Ejecuta una sentencia Insert mediante una transacción, para así mejorar el rendimiento.
     *
     * @param codigo
     * @param contrasena
     * @param especialidad
     * @param centro
     */
    public void crearEntrenador(String codigo, String contrasena, String especialidad, String centro) {
        // Create y/o abrir base de datos en modo escritura
        SQLiteDatabase db = getWritableDatabase();

        // Definir una transacción para mejorar el rendimiento y la consistencia de los datos.
        db.beginTransaction();
        try {
            //obtener fecha actual para registrarla
            Date fechaActual = new java.sql.Date(Calendar.getInstance().getTimeInMillis());

            //crear usuario en la BD, establecer valores para la transacción.
            ContentValues values = new ContentValues();
            values.put("codigo", codigo);
            values.put("contrasena", contrasena);

            //comprobar si los campos opcionales tienen contenido y si el contenido es válido. En caso negativo, no se añaden a la BD.

            //especialidad
            if (especialidad.isEmpty()) {
                //comprobar si está vacía, poner "N/A"
                values.put(KEY_ENT_ESP, "N/A");
            } else {
                values.put(KEY_ENT_ESP, especialidad);
            }

            //centro
            if (centro.isEmpty()) {
                values.put(KEY_ENT_CEN, "N/A");
            } else {
                values.put(KEY_ENT_CEN, centro);
            }
            //añadir fecha actual como fecha de registro
            values.put(KEY_ENT_FECHA, String.valueOf(fechaActual));
            // insertar en tabla deportista
            db.insertOrThrow(TABLE_ENT, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error al crear el usuario");
        } finally {
            //cerrar la transacción.
            db.endTransaction();
        }
    }

    /**
     * método para obtener el número de entrenados de un entrenador. Ejecuta una sentencia Select en la tabla Entrenado con la condición de que el ID de
     * entrenador coincida con el parámetro indicado.
     *
     * @param id
     * @return número de entrenados de ese entrenador
     */

    public int obtenerEntrenados(String id) {
        int cantidad = 0; //contador para la cantidad de entrenados

        //abrir base de datos
        SQLiteDatabase db = this.getWritableDatabase();
        //condición para la consulta.
        String where = "id_entrenador=?";
        String[] whereargs = new String[]{String.valueOf(id)};
        //crear cursor para almacenar el resultado de la consulta SQL
        Cursor cursor = db.query(
                TABLE_ENTRENADO,
                new String[]{"id"},
                where,
                whereargs,
                null, null, null

        );
        //recorrer el cursor, sumar 1 por cada fila para saber la cantidad total de usuarios Entrenado.
        if (cursor.moveToFirst()) {
            do {
                cantidad++;
            } while (cursor.moveToNext());
        }
        //cerrar el cursor
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        //devolver la cantidad
        return cantidad;

    }

    /**
     * método para comprobar si un usuario deportista existe. Ejecuta una sentenecia Select mediante un cursor donde si el resultado de iterar el cursor es
     * mayor a 0, significa que el usuario existe. Si es 0, significa que no se ha registrado ningún usuario con ese nombre.
     *
     * @param nombreTeclado
     * @return true si existe, false si no hay ningún usuario con ese nombre.
     */

    public boolean comprobarDeportista(String nombreTeclado) {
        //abrir la BD
        SQLiteDatabase db = this.getWritableDatabase();
        //preparar los argumentos para la consulta en strings
        String where = "nombre=?";
        String[] whereargs = new String[]{nombreTeclado};
        //iniciar cursor con la consulta y los parámetros.
        Cursor cursor = db.query(
                TABLE_DEP,
                new String[]{"nombre"},
                where,
                whereargs,
                null, null, null
        );
        //obtener la suma del contador.
        int contador = cursor.getCount();
        //cerrar el cursor
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        //si el cursor no está vacío (mayor que 0), el usuario existe, return true.
        return contador > 0;
    }

    /**
     * método para obtener el ID de un usuario deportista a partir del nombre. El nombre es único para cada usuario Deportista y se utiliza para iniciar sesión,
     * por lo que es un dato básico del usuario, pero en ocasiones se necesita conocer también su ID. Este método ejecuta una consulta select a la tabla Deportista
     * para retornar el ID del usuario cuyo nombre coincida con el parámetro.
     *
     * @param nombreTeclado
     * @return id del deportista
     */

    public String obtenerIdDeportista(String nombreTeclado) {
        //inicializar variable para almacenar ID
        String id = "";
        //abrir BD
        SQLiteDatabase db = this.getWritableDatabase();
        //consulta: select id from deportista where nombre = "nombre del usuario actual"
        String CONSULTA_DEPORTISTA =
                "SELECT ID FROM " + TABLE_DEP + " WHERE " + KEY_DEP_NOMBRE + " = '" + nombreTeclado + "'";
        //inicializar cursor con la consulta en String
        Cursor cursor = db.rawQuery(CONSULTA_DEPORTISTA, null);
        //mientras haya elementos en el cursor, iterar (solo debe haber 1)
        try {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getString(cursor.getColumnIndex(KEY_DEP_ID));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error al intentar cargar los datos del usuario de la base de datos");
        } finally { //cerrar el cursor
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        //devolver el ID del usuario
        return id;
    }

    /**
     * método para obtener el ID de un usuario entrenador a partir del código. Al igual que para el usuario Deportista con el nombre, el código de Entrenador
     * es un dato básico que se utiliza en el inicio de sesión e identifica al usuario. Pero para algunas acciones es necesario además el ID. Se implementó así
     * para en una versión futura de la app, permitir cambiar el código del entrenador.
     *
     * @param codigoTeclado
     * @return id del entrenador
     */

    public String obtenerIdEntrenador(String codigoTeclado) {
        //Inicializar String para almacenar luego el ID
        String id = "";
        SQLiteDatabase db = this.getWritableDatabase();
        //consulta: select id from deportista where nombre = "nombre del usuario actual"
        String CONSULTA_ENTRENADOR =
                "SELECT ID FROM " + TABLE_ENT + " WHERE " + KEY_ENT_COD + " = '" + codigoTeclado + "'";

        //iniciar cursor con la consulta en String.
        Cursor cursor = db.rawQuery(CONSULTA_ENTRENADOR, null);
        //iterar el cursor para establecer el valor de la variable ID con el valor de ese campo del cursor
        try {
            if (cursor.moveToFirst()) {
                do {
                    id = cursor.getString(cursor.getColumnIndex(KEY_ENT_ID));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error al intentar cargar los datos del entrenador de la base de datos");
        } finally { //cerrar el cursor
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        //devolver el id
        return id;
    }

    /**
     * método para comprobar si un usuario entrenador existe. Ejecuta una sentencia select con un cursor. Luego se cuenta el número de elementos del cursor, si es
     * mayor a 0, es porque existe el usuario. Si es 0, no hay coincidencias.
     *
     * @param nombreTeclado
     * @return true si existe, false si no.
     */

    public boolean comprobarEntrenador(String nombreTeclado) {
        //abrir BD
        SQLiteDatabase db = this.getWritableDatabase();
        //Strings con los parámetros para la consulta.
        String where = "codigo=?";
        String[] whereargs = new String[]{nombreTeclado};
        //iniciar cursor
        Cursor cursor = db.query(
                "entrenador",
                new String[]{"codigo"},
                where,
                whereargs,
                null, null, null
        );
        //obtener la cantidad de elementos del cursor
        int contador = cursor.getCount();
        //cerrar el cursor.
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        //si el cursor no está vacío (mayor que 0), el usuario existe, return true.
        return contador > 0;
    }

    //método para obtener los datos de un usuario entrenador de la BD
    public Entrenador obtenerDatosEntrenador(String codigo) {
        //objeto de tipo Entrenador vacío, para añadir los datos del cursor después
        Entrenador entrenador = new Entrenador();

        //consulta: select * from deportista where nombre = "nombre del usuario actual"
        String CONSULTA_ENTRENADOR =
                "SELECT * FROM " + TABLE_ENT + " WHERE " + KEY_ENT_COD + " = '" + codigo + "'";

        //abrir la base de datos
        SQLiteDatabase db = getReadableDatabase();
        //iniciar cursor
        Cursor cursor = db.rawQuery(CONSULTA_ENTRENADOR, null);
        try {
            //iterar el cursor (solo debe haber un elemento)
            if (cursor.moveToFirst()) {
                do {
                    //Asociar los valores de la BD obtenidos con el cursor al objeto Entrenador vacío.
                    entrenador.id = cursor.getString(cursor.getColumnIndex(KEY_ENT_ID));
                    entrenador.codigo = cursor.getString(cursor.getColumnIndex(KEY_ENT_COD));
                    entrenador.contrasena = cursor.getString(cursor.getColumnIndex(KEY_ENT_PASS));
                    entrenador.centro = cursor.getString(cursor.getColumnIndex(KEY_ENT_CEN));
                    entrenador.especialidad = cursor.getString(cursor.getColumnIndex(KEY_ENT_ESP));
                    entrenador.registro = cursor.getString(cursor.getColumnIndex(KEY_ENT_FECHA));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error al cargar los datos del entrenador de la base de datos");
        } finally {
            //cerrar el cursor
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        //devolver el objeto Entrenador con los datos
        return entrenador;
    }

    /**
     * método para eliminar un usuario deportista. Mediante el nombre del usuario. Utiliza una transacción para ejecutar la sentencia Delete en la tabla deportista.
     *
     * @param nombre
     */

    public void eliminarDeportista(String nombre) {
        //abrir la BD
        SQLiteDatabase db = getWritableDatabase();
        //consulta: detele * from deportista where nombre = "nombre del usuario actual"
        db.beginTransaction();
        //Strings con argumentos de la sentencia
        String whereClause = "nombre=?";
        String[] whereArgs = new String[]{String.valueOf(nombre)};
        //ejecutar sentencia
        try {
            // sentencia para eliminar el registro
            db.delete(TABLE_DEP, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error al eliminar el usuario deportista");
        } finally {
            //cerrar la transacción
            db.endTransaction();
        }
    }

    /**
     * método para eliminar un usuario entrenador mediante el código del usuario. Utiliza una transacción para ejecutar la sentencia Delete en la tabla entrenador.
     *
     * @param codigo
     */

    public void eliminarEntrenador(String codigo) {
        //abrir BD
        SQLiteDatabase db = getWritableDatabase();
        //abrir transtacción
        db.beginTransaction();
        //Strings con argumentos para la consulta
        String whereClause = "codigo=?";
        String[] whereArgs = new String[]{String.valueOf(codigo)};
        //ejecutar transacción con consulta.
        try {
            // sentencia para eliminar el registro
            db.delete(TABLE_ENT, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error al eliminar el usuario entrenador");
        } finally {
            //cerrar transacción
            db.endTransaction();
        }
    }


    /**
     * Método para registrar un ejercicio realizado por un deportista en BD. Ejecuta una sentencia Insert en la tabla realiza con los datos obtenidos como parámetros.
     *
     * @param codigoAct
     * @param idDeportista
     * @param tiempo
     * @param repeticiones
     */
    public void registrarEjercicioDeportista(int codigoAct, String idDeportista, double tiempo, int repeticiones) {
        // Create y/o abrir base de datos en modo escritura
        SQLiteDatabase db = getWritableDatabase();

        // Definir una transacción para mejorar el rendimiento y la consistencia de los datos.
        db.beginTransaction();
        try {
            //obtener fecha actual para registrarla
            Date fechaActual = new java.sql.Date(Calendar.getInstance().getTimeInMillis());

            //añadir datos de la actividad
            ContentValues values = new ContentValues();
            values.put("codigo_actividad", codigoAct);
            values.put("id_deportista", idDeportista);
            values.put("fecha", String.valueOf(fechaActual));
            values.put("tiempo", tiempo);
            values.put("repeticiones", repeticiones);

            // insertar en tabla realiza
            db.insertOrThrow(TABLE_REALIZA, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error al registrar la actividad del deportista en la BD");
        } finally {
            //cerrar transacción
            db.endTransaction();
        }
    }

    /**
     * Método para registrar un ejercicio realizado por un usuario entrenado en BD. Ejecuta una sentencia Insert en la tabla ejercita con los datos obtenidos como parámetros.
     *
     * @param codigoAct
     * @param idEntrenador
     * @param idEntrenado
     * @param tiempo
     * @param repeticiones
     */
    public void registrarEjercicioEntrenado(int codigoAct, String idEntrenador, String idEntrenado, double tiempo, int repeticiones) {
        // Create y/o abrir base de datos en modo escritura
        SQLiteDatabase db = getWritableDatabase();

        // Definir una transacción para mejorar el rendimiento y la consistencia de los datos.
        db.beginTransaction();
        try {
            //obtener fecha actual para registrarla
            Date fechaActual = new java.sql.Date(Calendar.getInstance().getTimeInMillis());

            //añadir datos de la actividad
            ContentValues values = new ContentValues();
            values.put("codigo_actividad", codigoAct);
            values.put("id_entrenador", idEntrenador);
            values.put("id_entrenado", idEntrenado);
            values.put("fecha", String.valueOf(fechaActual));
            values.put("tiempo", tiempo);
            values.put("repeticiones", repeticiones);

            // insertar en tabla ejercita
            db.insertOrThrow(TABLE_EJERCITA, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error al registrar la actividad del entrenado en la BD");
        } finally {
            //cerrar transacción
            db.endTransaction();
        }
    }


    /**
     * Método para obtener todos los entrenados de un entrenador en un ArrayList de objetos de tipo Entrenado. Ejecuta una sentencia select en la tabla Entrenado para
     * obtener todos los objetos de tipo Entrenado con sus campos que corresponden a un usuario entrenador (asociado al código que se pasa como parámetro).
     *
     * @param codigoEntrenador
     * @return
     */
    public ArrayList<Entrenado> obtenerEntrenados(int codigoEntrenador) {
        // Create y/o abrir base de datos en modo escritura
        SQLiteDatabase db = getWritableDatabase();

        //definir argumentos para la consulta.
        String[] misCampos = new String[]{"*"};
        String whereClause = KEY_ENTRD_ID_ENT + "=?";
        String[] whereArgs = new String[]{String.valueOf(codigoEntrenador)};

        //creo un arraylist de entrenados para almacenar todos los campos de los entrenados de ese mismo entrenador.
        ArrayList<Entrenado> entrenados = new ArrayList<>();
        //iniciar cursor para ejecutar la consulta.
        Cursor cur = db.query(TABLE_ENTRENADO, misCampos, whereClause, whereArgs, null, null, null);
        //iterar el cursor para obtener valores de cada columna
        try {
            if (cur.moveToFirst()) {
                do {
                    //asignar los valores a un objeto de tipo Entrenado
                    Entrenado nuevoEntrenado = new Entrenado();
                    nuevoEntrenado.id = cur.getString(cur.getColumnIndex(KEY_ENTRD_ID));//ID DEL ENTRENADO
                    nuevoEntrenado.nombre = cur.getString(cur.getColumnIndex(KEY_ENTRD_NOM));//nombre DEL ENTRENADO
                    nuevoEntrenado.estatura = cur.getString(cur.getColumnIndex(KEY_ENTRD_EST));//estatura DEL ENTRENADO
                    nuevoEntrenado.edad = cur.getString(cur.getColumnIndex(KEY_ENTRD_ED));//edad DEL ENTRENADO;
                    nuevoEntrenado.objetivo = cur.getString(cur.getColumnIndex(KEY_ENTRD_OBJ));//objetivo DEL ENTRENADO;
                    nuevoEntrenado.idEntrenador = cur.getString(cur.getColumnIndex(KEY_ENTRD_ID_ENT));//id del entrenador DEL ENTRENADO;
                    //añadir el objeto al arraylist
                    entrenados.add(nuevoEntrenado);
                } while (cur.moveToNext()); //buscar el siguiente elemento del cursor.
            }
        } catch (Exception e) {
            Log.d(TAG, "Error al intentar obtener los datos de los entrenados de la BD");
        } finally {
            //cerrar el cursor
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
        }
        //devolver la lista de entrenados
        return entrenados;
    }


    /**
     * método para comprobar si un usuario entrenado existe. Ejecuta una sentencia Select en la tabla Entrenado.
     *
     * @param nombreTeclado
     * @return true si hay algún usuario con ese nombre, false si no hay.
     */
    public boolean comprobarEntrenado(String nombreTeclado) {
        //abrir BD
        SQLiteDatabase db = this.getWritableDatabase();
        //argumentos para la consulta
        String where = "nombre=?";
        String[] whereargs = new String[]{nombreTeclado};
        //iniciar cursor
        Cursor cursor = db.query(
                TABLE_ENTRENADO,
                new String[]{"nombre"},
                where,
                whereargs,
                null, null, null
        );
        //obtener el número de elementos del contador
        int contador = cursor.getCount();
        //cerrar el cursor
        cursor.close();
        //si el cursor no está vacío (mayor que 0), el usuario existe, return true.
        return contador > 0;
    }


    /**
     * método para añadir un usuario entrenado a la base de datos. Ejecuta una sentencia Insert en la tabla entrenado mediante una transacción.
     *
     * @param entrenado
     */
    public void crearEntrenado(Entrenado entrenado) {
        // Create y/o abrir base de datos en modo escritura
        SQLiteDatabase db = getWritableDatabase();

        // Definir una transacción para mejorar el rendimiento y la consistencia de los datos.
        db.beginTransaction();
        try {
            //crear usuario en la BD
            ContentValues values = new ContentValues();
            values.put("nombre", entrenado.getNombre());

            //estatura
            if (!entrenado.getEstatura().isEmpty()) {
                values.put("estatura", entrenado.getEstatura());
            }

            //peso
            if (!entrenado.getEdad().isEmpty()) {
                values.put("edad", entrenado.getEdad());
            }

            //objetivo
            if (!entrenado.getObjetivo().isEmpty()) {
                values.put("objetivo", entrenado.getObjetivo());
            }

            //id entrenador
            values.put("id_entrenador", entrenado.getIdEntrenador());

            // insertar en tabla entrenado
            db.insertOrThrow(TABLE_ENTRENADO, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error al crear el usuario entrenado" + e);

        } finally {
            //cerrar transacción
            db.endTransaction();
        }
    }


    /**
     * método para eliminar un usuario entrenado. Ejecuta una sentencia delete en la tabla entrenado mediante una transacción.
     *
     * @param id
     */
    public void eliminarEntrenado(String id) {
        //abrir BD.
        SQLiteDatabase db = getWritableDatabase();
        //consulta: detele * from ENTRENADO where id = "id del usuario actual"
        //iniciar transacción
        db.beginTransaction();
        //argumentos para la sentencia
        String whereClause = "id=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        try {
            // sentencia para eliminar el registro
            db.delete(TABLE_ENTRENADO, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error al eliminar al usuario Entrenado");
        } finally {
            //finalizar transacción
            db.endTransaction();
        }
    }

    /**
     * método para recuperar los datos de un usuario Entrenado mediante el id del usuario. Para algunas acciones de la app es necesario conocer el id, además del nombre.
     *
     * @param id
     * @return
     */

    public Entrenado consultarDatosEntrenado(String id) {
        //nuevo objeto de tipo Entrenado vacío para volcar los datos más adelante.
        Entrenado entrenado = new Entrenado();
        //consulta: select * from ENTRENADO where id = "id del usuario actual"
        String CONSULTA_ENTRENADO =
                "SELECT * FROM " + TABLE_ENTRENADO + " WHERE " + KEY_ENTRD_ID + " = '" + id + "'";

        //abrir BD
        SQLiteDatabase db = getReadableDatabase();
        //iniciar cursor con argumentos de la consulta.
        Cursor cursor = db.rawQuery(CONSULTA_ENTRENADO, null);
        try {
            //recorrer el cursor
            if (cursor.moveToFirst()) {
                do {
                    //asignar campos de la BD al objeto Entrenado que se inicializó antes.
                    entrenado.id = cursor.getString(cursor.getColumnIndex(KEY_ENTRD_ID));
                    entrenado.nombre = cursor.getString(cursor.getColumnIndex(KEY_ENTRD_NOM));
                    entrenado.estatura = cursor.getString(cursor.getColumnIndex(KEY_ENTRD_EST));
                    entrenado.edad = cursor.getString(cursor.getColumnIndex(KEY_ENTRD_ED));
                    entrenado.objetivo = cursor.getString(cursor.getColumnIndex(KEY_ENTRD_OBJ));
                    entrenado.idEntrenador = cursor.getString(cursor.getColumnIndex(KEY_ENTRD_ID_ENT));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error al intentar cargar los datos del entrenado de la base de datos");
        } finally {
            //cerrar el cursor al acabar
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        //devolver el objeto Entrenado con los datos.
        return entrenado;
    }

    /**
     * método para recuperar los datos de un usuario DEPORTISTA mediante el id del usuario. Ejecuta una sentencia select de la tabla deportista y mediante el ID del
     * usuario obtiene un objeto Deportista con todos los datos de ese registro.
     *
     * @param id
     * @return
     */

    public Deportista consultarDatosDeportista(String id) {
        //nuevo objeto de tipo deportista para volvar los datos después
        Deportista deportista = new Deportista();
        //consulta: select * from ENTRENADO where id = "id del usuario actual"
        String CONSULTA_DEPORTISTA =
                "SELECT * FROM " + TABLE_DEP + " WHERE " + KEY_DEP_ID + " = '" + id + "'";
        // abrir la BD
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(CONSULTA_DEPORTISTA, null);

        //iterar el cursor
        try {
            if (cursor.moveToFirst()) {
                //asignar los valores de la consulta al objeto Deportista
                do {
                    deportista.id = cursor.getString(cursor.getColumnIndex(KEY_DEP_ID));
                    deportista.nombre = cursor.getString(cursor.getColumnIndex(KEY_DEP_NOMBRE));
                    deportista.contrasena = cursor.getString(cursor.getColumnIndex(KEY_DEP_PASS));
                    deportista.edad = cursor.getString(cursor.getColumnIndex(KEY_DEP_EDAD));
                    deportista.estatura = cursor.getString(cursor.getColumnIndex(KEY_DEP_ESTATURA));
                    deportista.peso = cursor.getString(cursor.getColumnIndex(KEY_DEP_PESO));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error al cargar los datos del deportista de la base de datos");
        } finally {
            //cerrar el cursor
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        //devolver el objeto deportista con los datos
        return deportista;
    }

    /**
     * método para recuperar los datos de un usuario ENTRENADOR mediante el id del usuario
     *
     * @param id
     * @return objeto Entrenador con los datos
     */
    public Entrenador consultarDatosEntrenador(String id) {
        //nuevo objeto de tipo entrenador para volvar los datos después
        Entrenador entrenador = new Entrenador();
        //consulta: select * from ENTRENADOR where id = "id del usuario actual"
        String CONSULTA_ENTRENADOR =
                "SELECT * FROM " + TABLE_ENT + " WHERE " + KEY_ENT_ID + " = '" + id + "'";
        // abrir la BD
        SQLiteDatabase db = getReadableDatabase();
        //inicializar el cursor con los datos de la consulta.
        Cursor cursor = db.rawQuery(CONSULTA_ENTRENADOR, null);
        //recorrer el cursor
        try {
            //asociar valores de la BD obtenidos con el cursor al objeto de tipo Entrenador
            if (cursor.moveToFirst()) {
                do {
                    entrenador.id = cursor.getString(cursor.getColumnIndex(KEY_ENT_ID));
                    entrenador.codigo = cursor.getString(cursor.getColumnIndex(KEY_ENT_COD));
                    entrenador.contrasena = cursor.getString(cursor.getColumnIndex(KEY_ENT_PASS));
                    entrenador.registro = cursor.getString(cursor.getColumnIndex(KEY_ENT_FECHA));
                    entrenador.especialidad = cursor.getString(cursor.getColumnIndex(KEY_ENT_ESP));
                    entrenador.centro = cursor.getString(cursor.getColumnIndex(KEY_ENT_CEN));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error al cargar los datos del Entrenador de la base de datos");
        } finally {
            //cerrar el cursor
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        //retornar objeto Entrenador con los datos
        return entrenador;
    }


    /**
     * Método para modificar usuario entrenado. Ejecuta una sentencia update en la tabla entrenado en la fila que coincida con el id pasado como parámetro.
     *
     * @param entrenado
     * @param id
     * @return
     */
    public int actualizarEntrenado(Entrenado entrenado, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ENTRD_NOM, entrenado.getNombre());
        values.put(KEY_ENTRD_EST, entrenado.getEstatura());
        values.put(KEY_ENTRD_ED, entrenado.getEdad());
        values.put(KEY_ENTRD_OBJ, entrenado.getObjetivo());

        // actualizar el perfil que corresponde a la ID pasada como parámetro
        return db.update(TABLE_ENTRENADO, values, KEY_ENTRD_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    /**
     * Método para modificar usuario deportista. Ejecuta una sentencia update en la tabla deportista.
     *
     * @param deportista
     * @param id
     * @return
     */

    public int actualizarDeportista(Deportista deportista, String id) {
        //abrir BD en modo escritura.
        SQLiteDatabase db = this.getWritableDatabase();

        //obtener los valores a actualizar.
        ContentValues values = new ContentValues();
        values.put(KEY_DEP_NOMBRE, deportista.getNombre());
        values.put(KEY_DEP_PASS, deportista.getContrasena());
        values.put(KEY_DEP_EDAD, deportista.getEdad());
        values.put(KEY_DEP_ESTATURA, deportista.getEstatura());
        values.put(KEY_DEP_PESO, deportista.getPeso());

        // actualizar el perfil que corresponde a la ID pasada como parámetro
        return db.update(TABLE_DEP, values, KEY_DEP_ID + " = ?",
                new String[]{String.valueOf(id)});
    }


    /**
     * Método para modificar usuario un entrenador. Ejecuta una sentencia update en la tabla entrenador para modificar
     * la fila que coincida con el id pasado como parámetro con los datos del objeto entrenador obtenido
     * como parámetro también.
     *
     * @param entrenador
     * @param id
     * @return
     */
    public int actualizarEntrenador(Entrenador entrenador, String id) {
        //abrir BD
        SQLiteDatabase db = this.getWritableDatabase();
        //obtener campos
        ContentValues values = new ContentValues();
        values.put(KEY_ENT_PASS, entrenador.getContrasena());
        values.put(KEY_ENT_ESP, entrenador.getEspecialidad());
        values.put(KEY_ENT_CEN, entrenador.getCentro());

        // actualizar el perfil que corresponde a la ID pasada como parámetro
        return db.update(TABLE_ENT, values, KEY_ENT_ID + " = ?",
                new String[]{String.valueOf(id)});
    }


}
