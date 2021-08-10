package Test;
/**
 * @author Prof Matias Garcia.
 * <p> Copyright (C) 2021 para <a href = "https://www.profmatiasgarcia.com.ar/"> www.profmatiasgarcia.com.ar </a>
 * - con licencia GNU GPL3.
 * <p> Este programa es software libre. Puede redistribuirlo y/o modificarlo bajo los términos de la
 * Licencia Pública General de GNU según es publicada por la Free Software Foundation, 
 * bien con la versión 3 de dicha Licencia o bien (según su elección) con cualquier versión posterior. 
 * Este programa se distribuye con la esperanza de que sea útil, pero SIN NINGUNA GARANTÍA, 
 * incluso sin la garantía MERCANTIL implícita o sin garantizar la CONVENIENCIA PARA UN PROPÓSITO
 * PARTICULAR. Véase la Licencia Pública General de GNU para más detalles.
 * Debería haber recibido una copia de la Licencia Pública General junto con este programa. 
 * Si no ha sido así ingrese a <a href = "http://www.gnu.org/licenses/"> GNU org </a>
 */
import Controlador.HibernateUtil;
import Modelo.Empleado;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class Test3 {

    public static void main(String[] args) {

        Session session = HibernateUtil.getSession().openSession();

        Empleado emp1 = new Empleado("Matias", "Garcia", "matias@gmail.com", "1525478965", 10);
        Empleado emp2 = new Empleado("Rocio", "Lopez", "rocio@gmail.com", "1512345678", 5);
        Empleado emp3 = new Empleado("Brianna", "Garcia", "brianna@gmail.com", "1523456789", 3);
        Empleado emp4 = new Empleado("Maria", "Ramirez", "maria@gmail.com", "1598765432", 8);
        Empleado emp5 = new Empleado("Julio", "Alvarez", "julio@gmail.com", "1587654321", 7);
        Empleado emp6 = new Empleado("Roberto", "Perez", "roberto@gmail.com", "1501234567", 6);
        Empleado emp7 = new Empleado("Blanca", "Madariaga", "blanca@gmail.com", "1576543210", 8);
        Empleado emp8 = new Empleado("Natalia", "Luque", "natalia@gmail.com", "1515975368", 5);
        Empleado emp9 = new Empleado("Julieta", "Salazar", "julieta@gmail.com", "1595135784", 7);
        Empleado emp10 = new Empleado("Emiliano", "Benites", "emiliano@gmail.com", "1523789456", 2);

        session.beginTransaction();
        session.save(emp1);
        session.save(emp2);
        session.save(emp3);
        session.save(emp4);
        session.save(emp5);
        session.save(emp6);
        session.save(emp7);
        session.save(emp8);
        session.save(emp9);
        session.save(emp10);
        session.getTransaction().commit();

        {
            System.out.println("----------- Uso de list() -----------");
            Query query = session.createQuery("SELECT p FROM Empleado p");
            List<Empleado> empleados = query.list();
            for (Empleado empleado : empleados) {
                System.out.println(empleado.toString());
            }
        }

        {
            System.out.println("----------- Uso de list() con datos escalares -----------");
            Query query = session.createQuery("SELECT p.id,p.nombre FROM Empleado p");
            List<Object[]> listDatos = query.list();
            for (Object[] datos : listDatos) {
                System.out.println(datos[0] + "--" + datos[1]);
            }
        }

        {
            System.out.println("----------- Uso de list() con un único dato escalar -----------");
            Query query = session.createQuery("SELECT p.nombre FROM Empleado p");
            List<Object> listDatos = query.list();
            for (Object datos : listDatos) {
                System.out.println(datos);
            }
        }

        {
            System.out.println("----------- Uso de uniqueResult -----------");
            Empleado empleado = (Empleado) session.createQuery("SELECT p FROM Empleado p WHERE id=1").uniqueResult();
            System.out.println("Empleado con Id 1= " + empleado.getApellido() + " " + empleado.getNombre());
        }

        {
            System.out.println("----------- Mostrar Empleados ordenados por Apellido -----------");
            int tamanyoPagina = 5; //limito a 5 la muestra
            Query query = session.createQuery("SELECT p FROM Empleado p Order By p.apellido");
            query.setMaxResults(tamanyoPagina);
            List<Empleado> empleadoes = query.list();

            for (Empleado empleado : empleadoes) {
                System.out.println(empleado);
            }
        }

        {
            System.out.println("----------- Calcular el nº de Empleados -----------");
            int tamanyoPagina = 10;
            long numTotalObjetos = (Long) session.createQuery("SELECT count(*) FROM Empleado p").uniqueResult();

            System.out.println("Nº de Empleados=" + numTotalObjetos);
        }

        {
            System.out.println("----------- Consultas con nombre -----------");
            Query query = session.getNamedQuery("findAllEmpleados");
            List<Empleado> empleadoes = query.list();
            for (Empleado empleado : empleadoes) {
                System.out.println(empleado.toString());
            }
        }

        {
            System.out.println("----------- Uso de AND y OR -----------");
            Query query = session.createQuery("SELECT p FROM Empleado p WHERE apellido='Garcia' AND (nombre='Matias' OR nombre='Brianna')");
            List<Empleado> empleados = query.list();
            for (Empleado empleado : empleados) {
                System.out.println(empleado.toString());
            }
        }

        {
            System.out.println("----------- Funciones -----------");
            Query query = session.createQuery("SELECT AVG(p.antiguedad),SUM(p.antiguedad),MIN(p.antiguedad),MAX(p.antiguedad),COUNT(*) FROM Empleado p");
            Object[] datos = (Object[]) query.uniqueResult();
            System.out.println("AVG(antiguedad)=" + datos[0]);
            System.out.println("SUM(antiguedad)=" + datos[1]);
            System.out.println("MIN(antiguedad)=" + datos[2]);
            System.out.println("MAX(antiguedad)=" + datos[3]);
            System.out.println("COUNT(*)=" + datos[4]);
        }

        {
            System.out.println("----------- Concatenar Strings -----------");
            Query query = session.createQuery("SELECT p.nombre || ' ' || p.apellido || ' ' || p.antiguedad FROM Empleado p WHERE id=5");
            List<Object> listDatos = (List<Object>) query.list();
            for (Object datos : listDatos) {
                System.out.println(datos);
            }
        }

        {
            System.out.println("----------- GROUP BY y HAVING -----------");
            Query query = session.createQuery("SELECT apellido,count(apellido) FROM Empleado p GROUP BY apellido HAVING count(apellido)>1 ORDER BY count(apellido)");
            List<Object[]> listDatos = (List<Object[]>) query.list();
            for (Object[] datos : listDatos) {
                System.out.println("El apellido \"" + datos[0] + "\" se repite  " + datos[1] + " veces");
            }
        }

        {
            System.out.println("----------- Subconsultas -----------");
            Query query = session.createQuery("SELECT nombre, antiguedad FROM Empleado c WHERE antiguedad >  (SELECT  AVG(antiguedad) FROM Empleado c2)");
            List<Object[]> listDatos = (List<Object[]>) query.list();
            for (Object[] datos : listDatos) {
                System.out.println("La antiguedad de \"" + datos[0] + "\" es de " + datos[1] + " años, siendo mayor que la media de años de todos los Empleados");
            }
        }

        {
            System.out.println("----------- Concatenación de Strings -----------");
            String nom = "Matias";
            String ape = "Garcia";

            Query query = session.createQuery("SELECT p FROM Empleado p where nombre='" + nom + "' AND  apellido='" + ape + "' ");
            List<Empleado> listado = query.list();
            for (Empleado empleado : listado) {
                System.out.println(empleado);
            }
        }

        {
            System.out.println("----------- Uso de setParameter -----------");
            String nom = "Brianna";
            String ape = "Garcia";

            Query query = session.createQuery("SELECT p FROM Empleado p where nombre=:nom AND apellido=:ape");
            query.setParameter("nom", nom);
            query.setParameter("ape", ape);

            List<Empleado> listado = query.list();
            for (Empleado empleado : listado) {
                System.out.println(empleado);
            }
        }

        {
            System.out.println("----------- Parametros encadenados -----------");
            String nom = "Julio";
            String ape = "Alvarez";

            Query query = session.createQuery("SELECT p FROM Empleado p where nombre=:nom AND apellido=:ape")
                    .setParameter("nom", nom)
                    .setParameter("ape", ape);

            List<Empleado> listado = query.list();
            for (Empleado empleado : listado) {
                System.out.println(empleado);
            }
        }
    }
}
