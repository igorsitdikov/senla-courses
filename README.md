## Bulletin Board

## Overview

Приложение "Система размещения частных объявлений"

## EER Diagram

![EER Diagram](EER Diagram.png)

## User Stories

### BB-1 Как "Пользователь", хочу зарегистрироваться, и если пользователя с таким e-mail не найдено, регистрируюсь

Request: `POST /api/sign-up`

```
{
    "firstName": "Сергей",
    "lastName": "Сергеев",
    "email": "serg.sergeev@example.com",
    "password": "sergiy",
    "phone": "+375295552311"
}
```

Response: `201 CREATED`

```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZXJnLnNlcmdlZXZAZXhhbXBsZS5jb20iLCJleHAiOjE2MDcxMjA1MzIsImlhdCI6MTYwNzA4NDUzMn0.nX1fXsh428XiiXDkw_NN1SlCy9yhm75CNEboEMZAi04"
}
```

### BB-2 Как "Пользователь", будучи зарегистрированным пользователем, я хочу войти в систему, и, если такой пользователь существует и пароль совпадает, войти в систему

Request: `POST /api/sign-in`
    
```    
{
    "email": "serg.sergeev@example.com",
    "password": "sergiy"
}
```

Response: `200 OK`

```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzZXJnLnNlcmdlZXZAZXhhbXBsZS5jb20iLCJleHAiOjE2MDcyOTgxOTMsImlhdCI6MTYwNzI2MjE5M30.qDVtUCoFKckdNCvAnEqvN5teCXyLyieDF92oGMIPWLc"
}
```

### BB-3 Как "Пользователь", будучи зарегистрированным пользователем, я хочу просмотреть свой профиль, и если авторизация пройдена, просматриваю профиль.

Request: `GET /api/users/${id}`

Где: `id=4`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`
  
Response: `200 OK`

```
{
    "id": 4,
    "firstName": "Сергей",
    "lastName": "Сергеев",
    "email": "serg.sergeev@example.com",
    "phone": "+375295552311",
    "role": "USER",
    "autoSubscribe": "DISABLE",
    "premium": "DISABLE",
    "balance": 0.00
}
```
  
### BB-4 Как "Пользователь", будучи зарегистрированным пользователем, я хочу отредактировать свой профиль, и если авторизация пройдена, редактирую свои данные в профиле.

Request: `PUT /api/users/${id}`

Где: `id=1`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`
  
```
{
    "email": "serg.sergeev@example.com",
    "firstName": "Сергей",
    "lastName": "Сергеев",
    "phone": "+37529111111",
    "autoSubscribe": "ACTIVE"
}
```
  
Response: `200 OK`
  
```
{
    "id": 4,
    "firstName": "Сергей",
    "lastName": "Сергеев",
    "email": "serg.sergeev@example.com",
    "phone": "+37529111111",
    "role": "USER",
    "autoSubscribe": "ACTIVE",
    "premium": "DISABLE",
    "balance": 0.00
}
```
  
### BB-5 Как "Пользователь", будучи зарегистрированным пользователем, я хочу сменить пароль, и если авторизация пройдена и пароли совпадают, происходит смена пароля.

Request: `PATCH /api/users/${id}`

Где: `id=4`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`
  
```
{
    "confirmPassword": "sergej",
    "newPassword": "sergej",
    "oldPassword": "sergiy"
}
```
  
Response: `200 OK`


### BB-6 Как "Пользователь", я хочу просмотреть список всех объявлений, имеющихся на сайте, в результате получаю список объявлений с ценой и названием.

Request: `GET /api/bulletins`

Response: `200 OK`

```
[
    {
        "id": 1,
        "title": "Продам отборный картофель, сорт «Вектор»",
        "price": 0.45,
        "createdAt": "06-12-2020 19:49",
        "seller": null,
        "sellerId": 4
    }
]
```

### BB-7 Как "Пользователь", я хочу просмотреть список отфильтрованных по цене объявлений, имеющихся на сайте, в результате получаю список объявлений с ценой от 25 до 50 рублей.

Request: `GET /api/bulletins?filter=price_gte:25,price_lte:50`

Response: `200 OK`

```
[
    {
        "id": 25,
        "title": "Eos ratione magni molestias non. Facilis perspiciatis rerum cumque velit necessitatibus voluptatem ea. Perferendis est deserunt veniam rem occaecati facilis iure.",
        "price": 39.81,
        "createdAt": "04-03-2018 06:22",
        "author": {
            "id": 1,
            "firstName": "Иван",
            "lastName": "Иванов",
            "email": "ivan.ivanov@mail.ru",
            "phone": "+375331234567",
            "role": "USER",
            "autoSubscribe": "ACTIVE",
            "premium": "DISABLE",
            "balance": 0.00
        }
    },
    {
        "id": 2,
        "title": "Продам соковыжималку \"Журавинка\" СВСП 102П",
        "price": 25.00,
        "createdAt": "29-11-2020 18:59",
        "author": {
            "id": 2,
            "firstName": "Петр",
            "lastName": "Петров",
            "email": "petr.petrov@yandex.ru",
            "phone": "+375337654321",
            "role": "USER",
            "autoSubscribe": "DISABLE",
            "premium": "DISABLE",
            "balance": 24.00
        }
    },
    {
        "id": 1,
        "title": "Продам свадебный сервиз",
        "price": 34.12,
        "createdAt": "29-11-2020 18:59",
        "author": {
            "id": 1,
            "firstName": "Иван",
            "lastName": "Иванов",
            "email": "ivan.ivanov@mail.ru",
            "phone": "+375331234567",
            "role": "USER",
            "autoSubscribe": "ACTIVE",
            "premium": "DISABLE",
            "balance": 0.00
        }
    }
]
```

### BB-8 Как "Пользователь", я хочу создать новое объявление, и создаю его.

Request: `POST /api/bulletins`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

```
{
    "title": "Продам отборный картофель, сорт «Вектор»",
    "price": 0.45,
    "sellerId": 1,
    "description": "БЕСПЛАТНАЯ доставка по г.Пружаны и району."
}
```

Response: `201 CREATED`

```
{
    "id": 1
}
```

### BB-9 Как "Пользователь", я хочу редактировать объявление, и если авторизация пройдена успешно, редактирую объявление.

Request: `PUT /api/bulletins/${id}`

Где: `id=4`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

```
{
    "title": "Продам отборный картофель, сорт «Вектор»",
    "price": 0.45,
    "sellerId": 4,
    "description": "БЕСПЛАТНАЯ доставка по г.Пружаны и району. Доставка по Брестской области – по договорённости.",
    "status": "OPEN"
}
```

Response: `200 OK`

```
{
    "id": 1,
    "title": "Продам отборный картофель, сорт «Вектор»",
    "price": 0.45,
    "createdAt": "06-12-2020 19:44",
    "seller": {
        "id": 4,
        "firstName": "Сергей",
        "lastName": "Сергеев",
        "email": "serg.sergeev@example.com",
        "phone": "+375295552311",
        "role": "USER",
        "autoSubscribe": "DISABLE",
        "premium": "DISABLE",
        "balance": 0.00
    },
    "sellerId": 4,
    "description": "БЕСПЛАТНАЯ доставка по г.Пружаны и району. Доставка по Брестской области – по договорённости.",
    "comments": [],
    "status": "OPEN"
}
```

### BB-10 Как "Пользователь", я хочу удалить объявление, и если авторизация пройдена успешно, удаляю его.

Request: `DELETE /api/bulletins/${id}`

Где: `id=1`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

Response: `200 OK`

### BB-11 Как "Пользователь", я хочу оставить комментарий под объявлением, и если пользователь зарегистрирован, оставляю комментарий.

Request: `POST /api/comments/`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

```
{
    "userId": 3,
    "bulletinId": 4,
    "comment": "Отличный картофель",
}
```

Response: `201 CREATED`

### BB-12 Как "Пользователь", я хочу просмотреть детали объявления, просматриваю.

Request: `GET /api/bulletins/${id}`

Где: `id=1`

Response: `200 OK`

```
{
    "id": 1,
    "title": "Продам отборный картофель, сорт «Вектор»",
    "price": 0.45,
    "createdAt": "06-12-2020 19:49",
    "seller": {
        "id": 4,
        "firstName": "Сергей",
        "lastName": "Сергеев",
        "email": "serg.sergeev@example.com",
        "phone": "+375295552311",
        "role": "USER",
        "autoSubscribe": "DISABLE",
        "premium": "DISABLE",
        "balance": 0.00
    },
    "sellerId": 4,
    "description": "БЕСПЛАТНАЯ доставка по г.Пружаны и району. Доставка по Брестской области – по договорённости.",
    "comments": [
        {
            "id": 1,
            "author": {
                "id": 1,
                "firstName": "Иван",
                "lastName": "Иванов",
                "email": "ivan.ivanov@mail.ru",
                "phone": "+375331234567",
                "role": "USER",
                "autoSubscribe": "DISABLE",
                "premium": "DISABLE",
                "balance": 0.00
            },
            "authorId": 1,
            "comment": "Отличный картофель. Разваристый.",
            "bulletinId": 1,
            "createdAt": "06-12-2020 19:55"
        }
    ],
    "status": "OPEN"
}
```

### BB-13 Как "Пользователь", я хочу написать сообщение продавцу, и если пользователь зарегистрирован, пишу сообщение.

Request: `POST /api/messages`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

```
{
    "dialogId": 1,
    "recipientId": 4,
    "senderId": 1,
    "message": "Возможна ли доставка 22.02.2021?"
}
```

Response: `200 OK`

```
{
    "id": 1
}
```

### BB-14 Как "Пользователь", я хочу удалить диалог, и если пользователь авторизован, удаляю диалог.

Request: `DELETE /api/dialogs/${id}`

Где: `id=3`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

Response: `200 OK`

### BB-15 Как "Пользователь", я хочу написать продавцу, и если пользователь авторизован, создаю новый диалог.

Request: `POST /api/dialogs`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

```
{
    "customerId": 1,
    "bulletinId": 1
}
```

Response: `201 CREATED`

```
{
    "id": 1
}
```

### BB-16 Как "Пользователь", я хочу получить список диалогов, и если пользователь авторизован, получаю.

Request: `GET /api/users/${id}/dialogs`

Где: `id=3`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

Response: `200 OK`

```
[
    {
        "id": 1,
        "title": "Продам отборный картофель, сорт «Вектор»",
        "sellerId": 3,
        "customerId": 1,
        "bulletinId": 4,
        "createdAt": "2020-09-12 12:00:32"
    }
]
```

### BB-17 Как "Пользователь", я хочу получить список сообщений в диалоге, и если пользователь авторизован, получаю.

Request: `GET /api/dialogs/${id}/messages`

Где: `id=1`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

Response: `200 OK`

```
[
    {
        "senderId": 1,
        "recipientId": 4,
        "dialogId": 1,
        "message": "Возможна ли доставка 22.02.2021?",
        "createdAt": "06-12-2020 20:02"
    },
    {
        "senderId": 4,
        "recipientId": 1,
        "dialogId": 1,
        "message": "Да.",
        "createdAt": "06-12-2020 20:05"
    }
]
```

### BB-18 Как "Пользователь", я хочу оценить продавца, и если пользователь зарегистрирован, ставлю оценку.

Request: `POST /api/votes`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

```
{
    "bulletinId": 1,
    "vote": 5,
    "voterId": 1
}
```

Response: `201 CREATED`

```
{
    "id": 1
}
```

### BB-19 Как "Пользователь", я хочу просмотреть историю продаж, и если пользователь авторизован, получаю список всех продаж.

Request: `GET /api/users/${id}/bulletins`

Где: `id=4`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

```
[
    {
        "id": 1,
        "title": "Продам отборный картофель, сорт «Вектор»",
        "price": 0.45,
        "createdAt": "06-12-2020 19:49",
        "seller": {
            "id": 4,
            "firstName": "Сергей",
            "lastName": "Сергеев",
            "email": "serg.sergeev@example.com",
            "phone": "+375295552311",
            "role": "USER",
            "autoSubscribe": "DISABLE",
            "premium": "DISABLE",
            "balance": 0.00
        },
        "sellerId": 4,
        "description": "БЕСПЛАТНАЯ доставка по г.Пружаны и району. Доставка по Брестской области – по договорённости.",
        "comments": [
            {
                "id": 1,
                "author": {
                    "id": 1,
                    "firstName": "Иван",
                    "lastName": "Иванов",
                    "email": "ivan.ivanov@mail.ru",
                    "phone": "+375331234567",
                    "role": "USER",
                    "autoSubscribe": "DISABLE",
                    "premium": "DISABLE",
                    "balance": 0.00
                },
                "authorId": 1,
                "comment": "Отличный картофель. Разваристый.",
                "bulletinId": 1,
                "createdAt": "06-12-2020 19:55"
            }
        ],
        "status": "OPEN"
    }
]
```

Response: `200 OK`

### BB-20 Как "Администратор", я хочу удалить объявление, нарушающее правила сайта, удаляю объявление.

Request: `DELETE /api/bulletins/${id}`

Где: `id=1`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

Response: `200 OK`


### BB-21 Как "Администратор", я хочу добавить тариф на премиум подписку, и если авторизуюсь, добавляю новый тариф.

Request: `POST /api/admin/tariffs`

```
{
    "price": 19.5,
    "term": 7,
    "description": "19.5$ за 7 дней"
}
```

Response: `201 CREATED`

```
{
    "id": 1
}
```

### BB-22 Как "Администратор", я хочу обновить тариф на премиум подписку, и если авторизуюсь, добавляю новый тариф.

Request: `PUT /api/admin/tariffs/${id}`

Где: `id=1`

```
{
    "price": 14.5,
    "term": 7,
    "description": "14.5$ за 7 дней"
}
```

Response: `200 OK`

```
{
    "id": 1,
    "price": 14.5,
    "term": 7,
    "description": "14.5$ за 7 дней"
}
```

### BB-23 Как "Пользователь", я хочу просмотреть возможные тарифы на премиум подписку, получаю список.

Request: `GET /api/tariffs`

```
[
    {
        "id": 1,
        "price": 5,
        "term": 1,
        "description": "5$ за 1 день"
    },
    {
        "id": 2,
        "price": 12,
        "term": 3,
        "description": "12$ за 3 дня"
    },
    {
        "id": 3,
        "price": 19.5,
        "term": 7,
        "description": "19.5$ за 7 дней"
    }
]
```

### BB-24 Как "Пользователь", я хочу купить премиум подписку на 7 дней, подписываюсь.

Request: `GET /api/tariffs/${id}?user_id={userId}`

Где: `id=3, userId=4`

Response: `200 OK`

### BB-25 Как "Администратор", я хочу просмотреть список всех пользователей, и если авторизуюсь, получаю список пользователей.

Request: `GET /api/admin/users`

Response: `200 OK`

```
[
    {
        "id": 1,
        "firstName": "Иван",
        "lastName": "Иванов",
        "email": "ivan.ivanov@mail.ru",
        "phone": "+375331234567",
        "role": "USER",
        "autoSubscribe": "DISABLE",
        "premium": "DISABLE",
        "balance": 0.00
    },
    {
        "id": 2,
        "firstName": "Петр",
        "lastName": "Петров",
        "email": "petr.petrov@yandex.ru",
        "phone": "+375337654321",
        "role": "USER",
        "autoSubscribe": "DISABLE",
        "premium": "DISABLE",
        "balance": 0.00
    },
    {
        "id": 3,
        "firstName": "Алексей",
        "lastName": "Алексеев",
        "email": "alex.alexeev@gmail.com",
        "phone": "+375333021232",
        "role": "ADMIN",
        "autoSubscribe": "DISABLE",
        "premium": "DISABLE",
        "balance": 0.00
    },
    {
        "id": 4,
        "firstName": "Сергей",
        "lastName": "Сергеев",
        "email": "serg.sergeev@example.com",
        "phone": "+37529111111",
        "role": "USER",
        "autoSubscribe": "ACTIVE",
        "premium": "DISABLE",
        "balance": 0.00
    }
]
```


### BB-25 Как "Администратор", я хочу изменить роль пользователя, и если авторизуюсь, меняю роль пользователя на "Администратор".

Request: `PATCH /api/admin/users/${id}?role=ADMIN`

Где: `id=2`

Response: `200 OK`

```
{
    "id": 2,
    "firstName": "Петр",
    "lastName": "Петров",
    "email": "petr.petrov@yandex.ru",
    "phone": "+375337654321",
    "role": "ADMIN",
    "autoSubscribe": "DISABLE",
    "premium": "DISABLE",
    "balance": 0.00
}
```