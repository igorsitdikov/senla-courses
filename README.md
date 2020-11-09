## Bulletin Board

## Overview

Приложение "Система размещения частных объявлений"

## User Stories

### BB-1 Как "Пользователь", хочу зарегистрироваться, и если пользователя с таким e-mail не найдено, регистрируюсь

Request: `POST /api/sign-up`

```
{
    "firstName": "Иван",
    "secondName": "Иванов",
    "email": "ivan.ivanov@mail.ru",
    "password": "123456",
    "phone": "+375331234567"
}
```

Response: `201 CREATED`

```
{
    "id": 1
}
```

### BB-2 Как "Пользователь", будучи зарегистрированным пользователем, я хочу войти в систему, и, если такой пользователь существует и пароль совпадает, войти в систему

Request: `POST /api/sign-in`
    
```    
{
    "email": "ivan.ivanov@mail.ru",
    "password": "123456"
}
```

Response: `200 OK`

```
{
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI2ODA3MjgsImlhdCI6MTU4MjY0NDcyOH0.oxNyf3jOPRoTuywoe2-oibyVxcisvOaPTWCaX56v9-0"
}
```

### BB-3 Как "Пользователь", будучи зарегистрированным пользователем, я хочу просмотреть свой профиль, и если авторизация пройдена, просматриваю профиль.

Request: `GET /api/users/${id}`

Где: `id=1`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`
  
Response: `200 OK`

```
{
    "id": 1,
    "firstName": "Иван",
    "secondName": "Иванов",
    "email": "ivan.ivanov@mail.ru",
    "phone": "+375331234567"
}
```
  
### BB-4 Как "Пользователь", будучи зарегистрированным пользователем, я хочу отредактировать свой профиль, и если авторизация пройдена, редактирую свои данные в профиле.

Request: `PUT /api/users/${id}`

Где: `id=1`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`
  
```
{
    "firstName": "Иван",
    "secondName": "Иванов",
    "email": "ivan.ivanov@yandex.ru",
    "phone": "+375337654321"
}
```
  
Response: `200 OK`
  
```
{
    "id": 1,
    "firstName": "Иван",
    "secondName": "Иванов",
    "email": "ivan.ivanov@yandex.ru",
    "phone": "+375337654321"
}
```
  
### BB-5 Как "Пользователь", будучи зарегистрированным пользователем, я хочу сменить пароль, и если авторизация пройдена и пароли совпадают, происходит смена пароля.

Request: `PATCH /api/users/${id}`

Где: `id=1`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`
  
```
{
    "oldPassword": "123456",
    "newPassword": "111111",
    "confirmPassword": "111111"
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
        "title": "Продам свадебный сервиз",
        "price": 34.12,
        "createdAt": "2020-09-12 12:00:32",
        "author": {
            "id": 2,
            "firstName": "Антон",
            "secondName": "Антонов",
            "email": "anton.antonov@yandex.ru",
            "phone": "+375332223344"
        }
    },
    {
        "id": 2,
        "title": "Продам соковыжималку "Журавинка" СВСП 102П",
        "price": 25,
        "createdAt": "2020-09-12 12:00:32",
        "author": {
            "id": 3,
            "firstName": "Петр",
            "secondName": "Петров",
            "email": "petr.petrov@yandex.ru",
            "phone": "+375337654321"
        },
    },
    {
        "id": 3,
        "title": "Продам хомяка",
        "price": 12,
        "createdAt": "2020-09-12 12:00:32",
        "author": {
            "id": 4,
            "firstName": "Сергей",
            "secondName": "Сергеев",
            "email": "sergei.sergeev@yandex.ru",
            "phone": "+375331111111"
        }
    }
]
```

### BB-7 Как "Пользователь", я хочу просмотреть список отфильтрованных по цене объявлений, имеющихся на сайте, в результате получаю список объявлений с ценой от 25 до 50 рублей.

Request: `GET /api/bulletins?filter=price_gte:25,price_lte:50`

Response: `200 OK`

```
[
    {
        "id": 1,
        "title": "Продам свадебный сервиз",
        "price": 34.12,
        "createdAt": "2020-09-12 12:00:32",
        "author": {
            "id": 2,
            "firstName": "Антон",
            "secondName": "Антонов",
            "email": "anton.antonov@yandex.ru",
            "phone": "+375332223344"
        }
    },
    {
        "id": 2,
        "title": "Продам соковыжималку "Журавинка" СВСП 102П",
        "price": 25,
        "createdAt": "2020-09-12 12:00:32",
        "author": {
            "id": 3,
            "firstName": "Петр",
            "secondName": "Петров",
            "email": "petr.petrov@yandex.ru",
            "phone": "+375337654321"
        },
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
    "author": {
        "id": 1,
        "firstName": "Иван",
        "secondName": "Иванов",
        "email": "ivan.ivanov@yandex.ru",
        "phone": "+375337654321"
    },
    "description": "БЕСПЛАТНАЯ доставка по г.Пружаны и району."
}
```

Response: `201 CREATED`

```
{
    "id": 4
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
    "description": "БЕСПЛАТНАЯ доставка по г.Пружаны и району. Доставка по Брестской области – по договорённости."
    "status": "CLOSE"
}
```

Response: `200 OK`

### BB-10 Как "Пользователь", я хочу удалить объявление, и если авторизация пройдена успешно, удаляю его.

Request: `DELETE /api/bulletins/${id}`

Где: `id=4`

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

Где: `id=4`

Response: `200 OK`

```
{
    "id": 4,
    "title": "Продам отборный картофель, сорт «Вектор»",
    "price": 0.45,
    "createdAt": "2020-09-12 12:00:32",
    "author": {
        "id": 1,
        "firstName": "Иван",
        "secondName": "Иванов",
        "email": "ivan.ivanov@yandex.ru",
        "phone": "+375337654321"
    },
    "description": "БЕСПЛАТНАЯ доставка по г.Пружаны и району.",
    "comments": [
        {
            "id": 1,
            "author": {
                "id": 3,
                "firstName": "Петр",
                "secondName": "Петров",
                "email": "petr.petrov@yandex.ru",
                "phone": "+375337654321"
            },
            "comment": "Отличный картофель",
            "createdAt": "2020-09-14 11:30:45"
        }
    ]
}
```

### BB-13 Как "Пользователь", я хочу написать сообщение продавцу, и если пользователь зарегистрирован, пишу сообщение.

Request: `POST /api/messages`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

```
{
    "senderId": 3,
    "recipientId": 1,
    "bulletinId": 4,
    "message": "Возможна ли доставка 22.02.2021?",
    "createdAt": "2020-09-12 12:00:32"
}
```

Response: `200 OK`

```
{
    "id": 1
}
```

### BB-14 Как "Пользователь", я хочу оценить продавца, и если пользователь зарегистрирован, ставлю оценку.

Request: `POST /api/stars`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

```
{
    "userId": 3,
    "bulletinId": 4,
    "stars": 5,
}
```

Response: `200 OK`

```
{
    "id": 1
}
```

### BB-15 Как "Пользователь", я хочу просмотреть историю продаж, и если пользователь авторизован, получаю список всех продаж.

Request: `POST /api/users/${id}/bulletins`

Где: `id=4`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

```
[
    {
        "id": 4,
        "title": "Продам отборный картофель, сорт «Вектор»",
        "price": 0.45,
        "author": {
            "id": 1,
            "firstName": "Иван",
            "secondName": "Иванов",
            "email": "ivan.ivanov@yandex.ru",
            "phone": "+375337654321"
        },
        "description": "БЕСПЛАТНАЯ доставка по г.Пружаны и району. Доставка по Брестской области – по договорённости."
    }
]
```

Response: `200 OK`

### BB-16 Как "Администратор", я хочу удалить объявление, нарушающее правила сайта, удаляю объявление.

Request: `DELETE /api/bulletins/${id}`

Где: `id=4`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk`

Response: `200 OK`


### BB-17 Как "Администратор", я хочу добавить тариф на премиум подписку, и если авторизуюсь, добавляю новый тариф.

Request: `POST /api/tariffs`

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
    "id": 3
}
```

### BB-18 Как "Пользователь", я хочу просмотреть возможные тарифы на премиум подписку, получаю список.

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

### BB-19 Как "Пользователь", я хочу купить премиум подписку на 7 дней, подписываюсь.

Request: `GET /api/tariffs/${id}?user_id={userId}`

Где: `id=3, userId=4`

Response: `200 OK`
