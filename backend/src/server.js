const express = require('express');
const bodyParser = require('body-parser');
const port = process.env.PORT || 5678;
const app = express();
const session = require('express-session');
const cors = require('cors');
require('dotenv').config({path:`${__dirname}/../.env`});
const router = require('./Routes/router');


const corsOptions = {
    origin: '*',
    Credentials: true,
    optionsSuccessStatus: 200
};


app.use(session({
    secret: 'resep_app',
    resave: false,
    saveUninitialized: true,
    cookie: { secure: false, maxAge:6000}
    }));


app.use(cors(corsOptions));


app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.use('/', router);

app.listen(port, () => {
    console.log('Server is running on port: ' + port);
});

