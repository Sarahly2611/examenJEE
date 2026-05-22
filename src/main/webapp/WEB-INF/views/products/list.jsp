<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des produits</title>
    <style>
        table, th, td { border: 1px solid black; border-collapse: collapse; padding: 8px; }
        .success { color: green; font-weight: bold; margin-bottom: 10px; }
    </style>
</head>
<body>
<h2>Gestion des produits</h2>

<c:if test="${not empty sessionScope.successMessage}">
    <div class="success">
            ${sessionScope.successMessage}
        <% session.removeAttribute("successMessage"); %>
    </div>
</c:if>

<a href="ProductServlet?action=addForm">➕ Ajouter un produit</a>
<br/><br/>

<table>
    <thead>
    <tr><th>ID</th><th>Nom</th><th>Prix</th><th>Quantité</th><th>Type</th><th>Actions</th></tr>
    </thead>
    <tbody>
    <c:forEach var="p" items="${products}">
        <tr>
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td>${p.price} €</td>
            <td>${p.quantity}</td>
            <td>${p.type}</td>
            <td>
                <a href="ProductServlet?action=detail&id=${p.id}">🔍 Détail</a>
                <a href="ProductServlet?action=editForm&id=${p.id}">✏️ Modifier</a>
                <a href="ProductServlet?action=delete&id=${p.id}" onclick="return confirm('Supprimer ?')">🗑️ Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>