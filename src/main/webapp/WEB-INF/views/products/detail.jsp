<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Détail du produit</title>
</head>
<body>
<h2>Détail du produit</h2>
<c:if test="${not empty product}">
    <p><strong>ID :</strong> ${product.id}</p>
    <p><strong>Nom :</strong> ${product.name}</p>
    <p><strong>Prix :</strong> ${product.price} €</p>
    <p><strong>Quantité :</strong> ${product.quantity}</p>
    <p><strong>Type :</strong> ${product.type}</p>
</c:if>
<br/>
<a href="ProductServlet?action=list">⬅ Retour à la liste</a>
</body>
</html>