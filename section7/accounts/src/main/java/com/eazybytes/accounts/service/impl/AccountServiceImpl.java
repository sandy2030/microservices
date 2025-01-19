package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import com.eazybytes.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl  implements IAccountService {
    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    /**
     * @param customerDto -CustomerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already registered with mobile number "
                    +customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        Accounts newAccount = createNewAccount(savedCustomer);
        accountsRepository.save(newAccount);
    }

    private Accounts createNewAccount(Customer customer){
            Accounts newAccount=new Accounts();
            newAccount.setCustomerId(customer.getCustomerId());
            long randomAccNo=1000000000L+new Random().nextInt(900000000);
            newAccount.setAccountNumber(randomAccNo);
            newAccount.setAccountType(AccountsConstants.SAVINGS);
            newAccount.setBranchAddress(AccountsConstants.ADDRESS);
            return newAccount;
    }
@Override
    public CustomerDto fetchAccount(String mobileNumber){
    Customer customer = customerRepository.findByMobileNumber(mobileNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Provided mobile number " + mobileNumber + " is not" +
                    "associated with  any customer"));
    Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId())
            .orElseThrow(()->new ResourceNotFoundException("Account not exists for given customer id "
                    +customer.getCustomerId()));
    CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
    AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(accounts, new AccountsDto());
    customerDto.setAccountsDto(accountsDto);
    return customerDto;
}

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated=false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (null!=accountsDto){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Account number " + accountsDto.getAccountNumber() + " not found "));
            // updated accounts with accountsDto
            AccountsMapper.mapToAccounts(accountsDto,accounts);
            // saved updated account
            accounts=accountsRepository.save(accounts);

            Long customerId=accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(
                            () -> new ResourceNotFoundException(
                                    "Customer id  " + customerId + " not found"));
            // update customer with customerDto modified by client
            CustomerMapper.mapToCustomer(customerDto,customer);
            // now save modified customer into db
            customerRepository.save(customer);
            isUpdated=true;
        }
        return isUpdated;
    }

    @Override
public boolean deleteAccount(String mobileNumber){
        boolean isDeleted=false;
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("mobile number " + mobileNumber + "  not found"));
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        isDeleted=true;
    return isDeleted;
}
}
