<%@ page contentType="text/html; charset=LATIN1" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>

<html>
    <head>
        <meta http-equiv="content-type" content="text/html; UTF-8" />

        <link rel="stylesheet" type="text/css" href="resources/css/style-novo.css">
        <link rel="stylesheet" href="resources/css/bootstrap.min.css">

        <script src="resources/js/jquery-2.1.0.min.js"></script>	
        <script src="resources/js/bootstrap.min.js"></script>
        
        <style type="text/css">

            #subsessao{
                background-color: rgb(53,140,244); 
                margin-bottom: 20px; 
                margin-left: 20px; 
                margin-right:20px; 
                border-radius:10px; 
                padding-bottom: 10px;
                box-shadow: 5px 5px 10px #888;
            }

            #conteudo-dinamico h1 {
                text-align: center; 
                padding-bottom: 20px;
                padding-top: 10px;
            }

            #subsessaoTitulo h2 {
                color: white; 
                text-align: center; 
                padding-top: 10px;
                margin-bottom: -10px;
            }

            .middle {
                float: left; 
                margin-left: 15px;
            }

            .middle a {               
                text-decoration: none;
                color: black;
            }

            .middle a:hover{
                color: white;
            }

            .data {
                float:right; 
                margin-right: 15px;
            }
        </style>
    </head>
    <%@include file="topo_paginas.jsp" %>	
    <div id="conteudo-dinamico">

        <c:if test="${segmento == 'pj'}">
            <h1>Pessoa Jurídica - ${sessao}</h1>
        </c:if>
        <c:if test="${segmento == 'pf'}">
            <h1>Pessoa Física - ${sessao}</h1>
        </c:if>
        <c:if test="${segmento == 'rede'}">
            <h1>Rede - ${sessao}</h1>
        </c:if>
        <c:if test="${segmento == 'agro'}">
            <h1>Agro - ${sessao}</h1>
        </c:if>
        <c:if test="${segmento == 'plane'}">
            <h1>Plane - ${sessao}</h1>
        </c:if>
        <c:if test="${segmento == 'drs'}">
            <h1>DRS - ${sessao}</h1>
        </c:if>
        <c:if test="${segmento == 'gov'}">
            <h1>Governo - ${sessao}</h1>
        </c:if>  

        <c:forEach var="subsessoes" items="${subsessoes}">
            <div id="subsessao">
                <div id="subsessaoTitulo" >		
                    <h2>${subsessoes.nome_subsessao}</h2>
                </div>
                <hr/>
                <div id="subsessaoItem">
                    <c:forEach var="lista" items="${lista}">
                        <c:if test="${lista.nome_subsessao == subsessoes.nome_subsessao}">
                            <div class="middle"><a href="${lista.link}">${lista.titulo} </a></div>
                            <div class="data">${lista.data}</div>
                            <div style="clear: both"></div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>

    </div>
    <div style="height: 20px"></div>
    <%@include file="final_paginas.jsp" %>
</html>