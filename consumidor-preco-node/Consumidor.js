const amqp = require('amqplib')
const fila = 'PRECO'

amqp.connect({
    host: 'localhost',
    port: 5672,
    username: 'admin',
    password: 1234
})
    .then((conexao) => {
        conexao.createChannel()
            .then((canal) => {
                canal.consume(fila, (menssagem) => {
                    console.log(menssagem.content.toString())
                }, {noAck: true})
            })
            .catch((erro) => console.log(erro))
    })
.catch((erro) => console.log(erro))


