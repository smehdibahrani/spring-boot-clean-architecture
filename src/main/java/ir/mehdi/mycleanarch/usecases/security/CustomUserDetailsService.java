package ir.mehdi.mycleanarch.usecases.security;


import ir.mehdi.mycleanarch.domain.models.Customer;
import ir.mehdi.mycleanarch.domain.repositories.CustomerRepository;
import ir.mehdi.mycleanarch.infrastructure.entities.CustomerEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomerEntity customer = customerRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", email)));

        return UserPrincipal.from(customer);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        CustomerEntity customer = customerRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", id)));

        return UserPrincipal.from(customer);
    }
}
