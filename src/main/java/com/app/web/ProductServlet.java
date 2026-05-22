package servlet;

import dao.ProductDAO;
import model.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "addForm":
                    showForm(req, resp, null);
                    break;
                case "editForm":
                    showEditForm(req, resp);
                    break;
                case "delete":
                    deleteProduct(req, resp);
                    break;
                case "detail":
                    showDetail(req, resp);
                    break;
                default:
                    listProducts(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500, "Erreur : " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("insert".equals(action)) {
                insertProduct(req, resp);
            } else if ("update".equals(action)) {
                updateProduct(req, resp);
            } else {
                resp.sendRedirect("ProductServlet?action=list");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(500);
        }
    }

    // Affiche la liste des produits → utilisation de list.jsp
    private void listProducts(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("products", productDAO.findAll());
        req.getRequestDispatcher("/list.jsp").forward(req, resp);
    }

    // Affiche le formulaire d'ajout
    private void showForm(HttpServletRequest req, HttpServletResponse resp, Product product)
            throws ServletException, IOException {
        if (product == null) product = new Product();
        req.setAttribute("product", product);
        req.setAttribute("actionType", "insert");
        req.getRequestDispatcher("/productForm.jsp").forward(req, resp);
    }

    // Affiche le formulaire de modification (pré-rempli)
    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Product p = productDAO.findById(id);
        if (p != null) {
            req.setAttribute("product", p);
            req.setAttribute("actionType", "update");
            req.getRequestDispatcher("/productForm.jsp").forward(req, resp);
        } else {
            addSuccessMessage(req, "Produit non trouvé");
            resp.sendRedirect("ProductServlet?action=list");
        }
    }

    // Insertion en base
    private void insertProduct(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String type = req.getParameter("type");

        Product p = new Product(name, price, quantity, type);
        productDAO.save(p);
        addSuccessMessage(req, "Produit ajouté avec succès !");
        resp.sendRedirect("ProductServlet?action=list");
    }

    // Mise à jour
    private void updateProduct(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String type = req.getParameter("type");

        Product p = new Product(name, price, quantity, type);
        p.setId(id);
        productDAO.update(p);
        addSuccessMessage(req, "Produit modifié avec succès !");
        resp.sendRedirect("ProductServlet?action=list");
    }

    // Suppression
    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        productDAO.delete(id);
        addSuccessMessage(req, "Produit supprimé avec succès !");
        resp.sendRedirect("ProductServlet?action=list");
    }

    // Détail d'un produit
    private void showDetail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Product p = productDAO.findById(id);
        if (p != null) {
            req.setAttribute("product", p);
            req.getRequestDispatcher("/productDetail.jsp").forward(req, resp);
        } else {
            addSuccessMessage(req, "Produit introuvable");
            resp.sendRedirect("ProductServlet?action=list");
        }
    }

    // Ajoute un message de succès en session
    private void addSuccessMessage(HttpServletRequest req, String msg) {
        HttpSession session = req.getSession();
        session.setAttribute("successMessage", msg);
    }
}