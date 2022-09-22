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
import Modelo.Producto;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
//import javax.persistence.Query;
import jakarta.persistence.Query;
import org.hibernate.Session;

public class Test {

    public static void main(String[] args) {
        int opcion = 0;
        Scanner scanner = new Scanner(System.in);
        Scanner leerNro = new Scanner(System.in);
        Producto producto;

        Session session = HibernateUtil.getSession().openSession();

        while (opcion != 6) {
            System.out.println("1. Crear Producto");
            System.out.println("2. Buscar Producto");
            System.out.println("3. Actualizar Producto");
            System.out.println("4. Eliminar Producto");
            System.out.println("5. Listar Productos");
            System.out.println("6. Salir");
            System.out.println("Elija una opción:");

            opcion = leerNro.nextInt();
            switch (opcion) {
                case 1:
                    producto = new Producto();
                    //producto.setId(null);
                    System.out.println("Ingrese el nombre del producto:");
                    producto.setNombre(scanner.nextLine());
                    System.out.println("Ingrese la descripción del producto:");
                    producto.setDescripcion(scanner.nextLine());
                    System.out.println("Ingrese la cantidad en stock del producto:");
                    producto.setCantidad(leerNro.nextInt());
                    System.out.println(producto);
                    session.beginTransaction();
                    session.save(producto);
                    session.getTransaction().commit();
                    System.out.println("Producto registrado..");
                    System.out.println();
                    break;

                case 2:
                    System.out.println("Ingrese el id del producto a buscar:");
                    producto = new Producto();
                    producto = session.find(Producto.class, leerNro.nextInt());
                    if (producto != null) {
                        System.out.println(producto);
                        System.out.println();
                    } else {
                        System.out.println();
                        System.out.println("Producto no encontrado... Lista de productos completa");
                        List<Producto> listaProductos = new ArrayList<>();
                        Query query = session.createQuery("SELECT p FROM Producto p");
                        listaProductos = query.getResultList();
                        for (Producto p : listaProductos) {
                            System.out.println(p);
                        }

                        System.out.println();
                    }

                    break;
                case 3:
                    System.out.println("Ingrese el id del producto a actualizar:");
                    producto = new Producto();

                    producto = session.find(Producto.class, leerNro.nextInt());
                    if (producto != null) {
                        System.out.println(producto);
                        System.out.println("Ingrese el nuevo nombre del producto:");
                        producto.setNombre(scanner.nextLine());
                        System.out.println("Ingrese la nueva descripción del producto:");
                        producto.setDescripcion(scanner.nextLine());
                        System.out.println("Ingrese la cantidad en stock del producto:");
                        producto.setCantidad(leerNro.nextInt());
                        session.beginTransaction();
                        session.merge(producto);
                        session.getTransaction().commit();
                        System.out.println("Producto actualizado..");
                        System.out.println();
                    } else {
                        System.out.println("Producto no encontrado....");
                        System.out.println();
                    }
                    break;
                case 4:
                    System.out.println("Ingrese el id del producto a eliminar:");
                    producto = new Producto();

                    producto = session.find(Producto.class, leerNro.nextInt());
                    if (producto != null) {
                        System.out.println(producto);
                        session.beginTransaction();
                        session.remove(producto);
                        session.getTransaction().commit();
                        System.out.println("Producto eliminado...");
                    } else {
                        System.out.println("Producto no encontrado...");
                    }
                    break;
                case 5:
                    session.beginTransaction();
                    List<Producto> productos = session.createQuery("FROM productos", Producto.class).getResultList();
                    session.getTransaction().commit();
                    System.out.println(productos);
                    break;
                case 6:
                    session.close();
                    break;

                default:
                    System.out.println("Opción no válida\n");
                    break;
            }
        }
    }

}
