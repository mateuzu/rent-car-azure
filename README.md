# Sistema de Locação de Veículos com Azure 🚗

Este é um projeto simples de locação de veículos desenvolvido para praticar o uso de diversos recursos da Azure. A solução consiste em uma aplicação front-end em HTML, CSS e JavaScript que envia dados para uma API Node.js, que interage com uma série de serviços do Azure para simular um processo de locação e pagamento de veículos.

## Arquitetura do Sistema 🎯

A ideia é criar uma aplicação totalmente desacoplada. Para isso, a arquitetura é composta por diversas tecnologias e serviços da Azure, organizados da seguinte forma:
- Front-end: Container App com uma página simples em HTML, CSS e JavaScript que coleta os dados da locação de veículos.
- API (Node.js): Container App que recebe os dados da locação e os envia para uma fila do Azure Service Bus.
- Azure Functions:
    - Rent Process: Uma função em Java que monitora a fila de locações e salva os dados em um banco de dados Azure SQL, enviando posteriormente os dados para uma fila de pagamentos.
    - Payment Process: Outra função em Java que monitora a fila de pagamentos, simula um pagamento e salva os dados no Azure CosmosDB, antes de enviar os dados de notificação para uma fila.
- Logic App: Monitora a fila de notificações e envia um e-mail para o cliente com os detalhes da locação utilizando o serviço de Outlook.


## Recursos Utilizados 💡

O projeto utiliza os seguintes recursos da Azure:

- Azure Container Registry (ACR): Para hospedar as imagens de containers do front-end e da API.
- Azure Container Apps: Para hospedar os containers do front-end e da API. Ambos são executados em containers gerenciados pelo Azure Container Apps.
- Azure Service Bus: Para orquestrar a comunicação entre as diferentes partes do sistema, com filas fila-locacao, fila-pagamentos e fila-notificacao.
- Azure SQL Database: Para armazenar os dados de locação dos veículos.
- Azure CosmosDB: Para armazenar os dados de pagamento, simulando o processo de pagamento.
- Application Insights: Para monitorar e registrar as métricas de desempenho da aplicação.
- Azure Key Vault: Para armazenar credenciais e dados sensíveis de forma segura.
- Azure Functions: Funções em Java para processar os dados da locação e do pagamento.
- Azure Logic Apps: Para enviar e-mails de notificação para os clientes quando o pagamento for aprovado.

## Tecnologias Utilizadas 💻
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![Markdown](https://img.shields.io/badge/Markdown-000?style=for-the-badge&logo=markdown)
![Azure](https://img.shields.io/badge/Azure-blue?style=for-the-badge&logo=microsoft%20azure&logoColor=blue&labelColor=FFFFFF&link=https%3A%2F%2Fimages.app.goo.gl%2FK7PN1jYJd57x4q7A8)
![Docker](https://img.shields.io/badge/Docker-000?style=for-the-badge&logo=docker&color=white)
![Git](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37.svg?style=for-the-badge&logo=Postman&logoColor=white)

## Fluxo de Dados ⚙️

- O front-end coleta as informações da locação do veículo (dados do cliente, veículo, etc.) e envia para a API Node.js.
- A API envia os dados para a fila fila-locacao no Azure Service Bus.
- A Azure Function Rent Process monitora a fila fila-locacao, processa os dados, cria um objeto Rental e salva as informações no Azure SQL Database.
- Em seguida, a função envia os dados para a fila fila-pagamentos.
- A Azure Function Payment Process monitora a fila fila-pagamentos, processa o pagamento (simulado de forma aleatória), cria um objeto Payment e salva os dados no Azure CosmosDB.
- Caso o pagamento seja aprovado, a função envia uma notificação para a fila fila-notificacao.
- O Logic App monitora a fila fila-notificacao, captura o e-mail do cliente e envia uma notificação com os detalhes da locação utilizando o Outlook.

------------------------------------------------------------

## Conclusão 📌

Este projeto foi desenvolvido para principalmente para praticar a integração de diversos serviços da Azure no contexto de uma aplicação de locação de veículos. A solução utiliza containers para o front-end e a API, Azure Service Bus para orquestrar as mensagens entre diferentes componentes, Azure SQL e CosmosDB para persistência de dados, e Azure Functions para processar as locações e pagamentos de forma escalável e eficiente.

Com esse projeto, pude obter uma boa noção no uso de ferramentas como Azure Container Apps, Azure Service Bus, Azure Functions, entre outros recursos da Azure, além de aplicar conceitos de microsserviços, filas e automação.

### Meus Contatos 📞
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/mateus-ferreira-costa/)
[![Portfolio](https://img.shields.io/badge/Portfolio-FF5722?style=for-the-badge&logo=todoist&logoColor=white)](https://mateus-portifolio.vercel.app/)
