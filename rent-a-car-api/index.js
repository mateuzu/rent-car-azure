const express = require('express');
const cors = require('cors');
const {DefaultAzureCredential} = require("@azure/identity");
const {ServiceBusClient} = require("@azure/service-bus");
const {format} = require('date-fns');
require('dotenv').config();
const app = express();
app.use(cors());
app.use(express.json());

const serviceBusConnection = process.env.SERVICE_BUS_CONNECTION;
console.log("ServiceBusConnection: " , serviceBusConnection)
const queueName = process.env.SERVICE_BUS_CONNECTION_QUEUE_NAME;
console.log("Queue name: " , queueName)

app.post('/api/locacao', async (req, res) => {
    const { nome, email, modelo, ano, tempoAluguel} = req.body;

    const mensagem = {
        nome,
        email,
        modelo,
        ano,
        tempoAluguel,
        data: format(new Date(), 'dd/MM/yyyy')
    };

    try {
        const credential = new DefaultAzureCredential();
        const sbClient = new ServiceBusClient(serviceBusConnection);
        const sender = sbClient.createSender(queueName);

        const message = {
            body: mensagem,
            contentType: "application/json",
            label: "locacao"
        };

        await sender.sendMessages(message);
        await sender.close();
        await sbClient.close();

        res.status(200).send("Locação enviada para a fila com sucesso");
    } catch (err) {
        console.error("Erro ao enviar mensagem para a fila:", err);
        res.status(500).send("Erro interno");
    }
});

app.listen(3001, () => console.log("Aplicação rodando na porta 3001"));