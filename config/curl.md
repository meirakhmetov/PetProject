### curl samples (application deployed in application context `topjava`).
> For windows use `Git Bash`

#### get All Users
`curl -s http://localhost:8080/PetProject/rest/admin/users --user admin@ok.kz:admin`

#### get Users 100001
`curl -s http://localhost:8080/PetProject/rest/admin/users/100001 --user admin@ok.kz:admin`

#### get All Meals
`curl -s http://localhost:8080/PetProject/rest/profile/meals --user user@ok.kz:password`

#### get Meals 100003
`curl -s http://localhost:8080/PetProject/rest/profile/meals/100003 --user user@ok.kz:password`

#### filter Meals
`curl -s "http://localhost:8080/PetProject/rest/profile/meals/filter?startDate=2023-01-01&startTime=07:00:00&endDate=2023-01-02&endTime=11:00:00" --user user@ok.kz:password`

#### get Meals not found
`curl -s -v http://localhost:8080/PetProject/rest/profile/meals/100008 --user user@ok.kz:password`

#### delete Meals
`curl -s -X DELETE http://localhost:8080/PetProject/rest/profile/meals/100002 --user user@ok.kz:password`

#### create Meals
`curl -s -X POST -d '{"dateTime":"2023-01-03T12:00","description":"Created lunch","calories":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/PetProject/rest/profile/meals --user user@ok.kz:password`

#### update Meals
`curl -s -X PUT -d '{"dateTime":"2023-01-01T07:00", "description":"Updated breakfast", "calories":200}' -H 'Content-Type: application/json' http://localhost:8080/PetProject/rest/profile/meals/100003 --user user@ok.kz:password`

#### validate with Error
`curl -s -X POST -d '{}' -H 'Content-Type: application/json' http://localhost:8080/PetProject/rest/admin/users --user admin@ok.kz:admin`
