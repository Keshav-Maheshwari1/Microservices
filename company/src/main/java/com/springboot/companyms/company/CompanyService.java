package com.springboot.companyms.company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    Company getCompanyById(Long id);
    Boolean updateCompany(Long id,Company updatedCompany);
    void createCompany(Company company);

    Boolean deleteCompanyById(Long id);
}
