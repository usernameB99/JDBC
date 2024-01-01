This is my management system that is connected to my mysql database with jdbc.
I used dao classes for the implementation.
It manages households, persons and pets.
Households contain multiple persons and persons can have multiple pets.
Households can be created, updated and deleted, aswell as chosen from a list of all households to add persons.
Persons can be created, updated and deleted, aswell as chosen from the list of the household to add pets.
Pets can be created, updated and deleted, aswell as previewed by a list from a person.
Every object creatd gets a uniqe id, so having the same name, age, adress, etc. is no problem.
