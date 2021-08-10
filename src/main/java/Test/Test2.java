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
import Modelo.Producto;
import Controlador.ControladorProducto;
import Controlador.HibernateUtil;
import org.hibernate.Session;

public class Test2 {

    public static void main(String[] args) {
        System.out.println("Iniciando conexion MySQL");
        Session session = HibernateUtil.getSession().openSession();

        ControladorProducto cp = new ControladorProducto(session);

        Producto prod1 = new Producto("HD 1TB", "HD WesternDigital 1TB", 15);
        cp.save(prod1);
        Producto prod2 = new Producto("Mouse Bluetooth", "Genius A34", 48);
        cp.save(prod2);
        Producto prod3 = new Producto("Pendrive 64GB", "Kingstone Travel1", 120);
        cp.save(prod3);
        Producto prod4 = new Producto("RAM 16GB", "Fury DDR4 HyperX", 70);
        cp.save(prod4);

        System.out.println("--------TODOS LOS PRODUCTOS--------");
        System.out.println(cp.findAll());

        System.out.println("--------PRODUCTO NRO 4--------");
        System.out.println(cp.findOneById(4));

        Producto aux1 = cp.findOneById(4);
        aux1.setCantidad(300);
        cp.update(aux1);

        System.out.println("--------TODOS LOS PRODUCTOS--------");
        System.out.println(cp.findAll());

        Producto aux2 = cp.findOneById(2);
        cp.delete(aux2);

        System.out.println("--------TODOS LOS PRODUCTOS--------");
        System.out.println(cp.findAll());

        session.close();
        System.out.println("Se cerro conexion MySQL");
    }
}
