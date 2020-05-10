## Jupiter2

Jupiter2 is a Job Search System based on GitHub Jobs API provides.
It was initially an upgrade from [Jupiter](https://github.com/zsxing99/Jupiter)
, a Ticket Recommendation. However, facing the economy downturn, I decided to use 
Github jobs API to make a job recommendation.

### Cloud Deployment

The project is deployment on AWS. Click this [link](http://54.193.31.55/Jupiter2/) to
access the demo.

### Project Demo

#### Desktop

![Login](./demo/screenshots/desktop/Annotation%202020-05-09%20181058.png)

![Register](./demo/screenshots/desktop/Annotation%202020-05-09%20181059.png)

![Welcome](./demo/screenshots/desktop/Annotation%202020-05-09%20181060.png)

![Search](./demo/screenshots/desktop/Annotation%202020-05-09%20181061.png)

![Favorite](./demo/screenshots/desktop/Annotation%202020-05-09%20181062.png)

![Recommend](./demo/screenshots/desktop/Annotation%202020-05-09%20181063.png)

#### Phone

![Login](./demo/screenshots/phone/Annotation%202020-05-09%20181064.png)

![Register](./demo/screenshots/phone/Annotation%202020-05-09%20181065.png)

![Favorite](./demo/screenshots/phone/Annotation%202020-05-09%20181067.png)

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
