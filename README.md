## Jupiter2

Jupiter2 is a Job Search System based on GitHub Jobs API provides.
It was initially an upgrade from [Jupiter](https://github.com/zsxing99/Jupiter)
, a Ticket Recommendation. However, facing the economy downturn, I decided to use 
Github jobs API to make a job recommendation.

### Cloud Deployment

The project is deployment on AWS. Click this [link](http://54.193.31.55/Jupiter2/) to
access the demo.

### Project Structure
It is a full stack project covering front-end/backend/database

#### Front End
Antd Design Pro framework is adapted here with some customization. A fully
responsive and functional webpage is built with ReactJs.

##### Web Router
```
pages
├── user
│   ├── login
│   └── register
├── welcome
├── search
├── favorite
└── recommendation
```


The repo for [front end](https://github.com/zsxing99/Jupiter2-front-end).

#### Backend
Backend is built with Java and Tomcat to support RPC, some external queries,
and link the database.

##### RPC
```
servlet
├── login
├── logout
├── register
├── searchItem
├── recommendItem
└── itemHistory
```

##### External
```
external
├── GitHub Jobs
└── MonkeyLearn (NLP)
```

#### Database
MongoDB is used here for scalability.
