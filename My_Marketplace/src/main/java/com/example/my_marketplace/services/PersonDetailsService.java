package com.example.my_marketplace.services;

import com.example.my_marketplace.models.Order;
import com.example.my_marketplace.models.Person;
import com.example.my_marketplace.repositories.PersonRepository;
import com.example.my_marketplace.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;


    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Получаем пользователя из таблицы по логину с формы аутентификации
        Optional<Person> person = personRepository.findByLogin(username);
        // Если пользователь не был найден
        if(person.isEmpty()){
            // Выбрасываем исключение что данный пользователь не найден
            // Данное исключение будет поймано Spring Security и сообщение будет выведено на страницу
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new PersonDetails(person.get());
    }

    // Метод выводит всех пользователей
    public List<Person> getAllUsers (){
        return personRepository.findAll();
    }

    // Метод находит польщователя по ID
    @Transactional
    public Person findByID(int id){
        Optional<Person> person_id = personRepository.findById(id);
        return person_id.orElse(null);
    }

    // Метод позволяет поменять роль пользователю
    @Transactional
    public void updateUserRole (String role, Person person) {
        person.setRole(role);
        personRepository.save(person);
    }

}
