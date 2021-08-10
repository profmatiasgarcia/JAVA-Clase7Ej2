package Controlador;
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
import Modelo.Empleado;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Operaciones {

    public Operaciones() {
    }

    public void altaUsuarios(Empleado emp) {

        Session session = HibernateUtil.getSession().openSession();
        System.out.println("Conexion a BD");
        Transaction tx = session.beginTransaction();
        session.save(emp);
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "El Empleado fue grabado correctamente");
    }

    public void bajaUsuarios(Empleado emp) {

        SessionFactory Misesion = HibernateUtil.getSession();
        Session session;
        session = Misesion.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(emp);
        tx.commit();
        session.close();
        JOptionPane.showMessageDialog(null, "Empleado dato fue eliminado correctamente");
    }
}
