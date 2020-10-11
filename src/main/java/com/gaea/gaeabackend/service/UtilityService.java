/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gaea.gaeabackend.service;

import com.gaea.gaeabackend.dto.ProductDTO;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ndry93
 */
@Service
public class UtilityService {
    
    
    public List<ProductDTO> processUploadedCreateProductBatchFile(MultipartFile multipartFile) throws IOException {
        return null;
        
    }
        
//		HttpStatus httpStatus;
//		File file = convertMultiPartToFile(multipartFile);
//		String uuid = null;
//		ObjectMapper mapper = new ObjectMapper();
//		
//		List<SCBUsers> newRaiUsersList = new ArrayList<SCBUsers>();
//
//		try (Reader reader = new FileReader(file);) {
//			@SuppressWarnings("unchecked")
//			CsvToBean<SCBUsers> csvToBean = new CsvToBeanBuilder<SCBUsers>(reader).withType(SCBUsers.class)
//					.withIgnoreLeadingWhiteSpace(true).withSkipLines(1).build();
//			List<SCBUsers> scbUsersList = csvToBean.parse();
//			
//			List<Banks> allBanks = (List<Banks>) iRaiBanksRepository.findAll();
//
//			if (allBanks != null && !allBanks.isEmpty()) {
//				
//				for ( SCBUsers scbUsers : scbUsersList.subList( 0, scbUsersList.size() ) )
//				{
//					log.debug("bank id : "+bankid);
//					log.debug("user id : "+scbUsers.getUserID());
//					log.debug("firstname : "+scbUsers.getFirstName());
//					log.debug("lastname : "+scbUsers.getLastName());
//					log.debug("active : "+scbUsers.getActive());
//					log.debug("password : "+scbUsers.getPassword());
//					log.debug("workemail : "+scbUsers.getWorkEmail());
//
//					log.debug("count file : "+scbUsersList.size());
//					log.debug("file : "+file.getName());
//					
//					Boolean usersExist = iRaiUsersRepository.existsById(scbUsers.getUserID());							
//					uuid = GUIDUtil.generateGUID();
//					
//					//insert user
//					if (usersExist == false) {
//						String bankAcctNo1 = "123"+scbUsers.getUserID()+"1";//default
//						String bankAcctNo2 = "245"+scbUsers.getUserID()+"2";
//						String last4DigitAcctNo = bankAcctNo1.substring(bankAcctNo1.length() - 4 );
//						
//						Banks raiBanks1 = iRaiBanksRepository.findOneById(2);//default
//						if(raiBanks1 == null) {
//							log.debug("bank id {} not found",2);
//							continue;
//							}
//						
//				        Banks raiBanks2 = iRaiBanksRepository.findOneById(3);
//				        if(raiBanks2 == null) {
//							log.debug("bank id {} not found",3);
//							continue;
//							}
//				        
//				        
////						//create wallet post
//						WalletResponse createWalletResponse = new WalletResponse();
////						List<String> args = List.of(uuid,"SC","Active","Pass","GBP","0");
//						List<String> args = List.of
//								(uuid,"SC","Active","Pass","GBP","0",raiBanks1.getCode(),last4DigitAcctNo,Long.toString(System.currentTimeMillis()));
//						createWalletResponse = makeInvokeRequest(args, createMethod);
//								
//						log.debug("###### CREATE WALLET RESPONSE ######");		
//						log.debug("return code : "+createWalletResponse.getReturnCode());	
//						log.debug("tx id : "+createWalletResponse.getResult().getPayload());						
//						log.debug("###### CREATE WALLET RESPONSE ######");	
//						if (!createWalletResponse.getReturnCode().equalsIgnoreCase("success")) {
//							log.debug("###### SKIP USER ######");	
//							continue;
//						}
//						
//						//insert user						
//						Users users = new Users().builder()
//								.userId(scbUsers.getUserID())
//								.firstName(scbUsers.getFirstName())
//								.lastName(scbUsers.getLastName())
//								.email(scbUsers.getWorkEmail())
//								.walletId(uuid)
//								.featureFlag_1(flag1)
//								.featureFlag_2(flag2)
//								.featureFlag_3(flag3)
//								.build();						
////						//save to db
//						iRaiUsersRepository.save(users);
//						log.debug("Insert RAI_USERS ,User ID : "+scbUsers.getUserID()+" created");
//						
//						
//						//insert bank account 1		
//						BankAccounts bankAccounts1 = new BankAccounts().builder()
//				        		.users(users)
//				        		.banks(raiBanks1)
//				        		.isDefault(true)
//				        		.balance(balance)
//				        		.currency("GBP")
//				        		.reservedBalance(new BigDecimal(0))
//				        		.accountNo(bankAcctNo1)
//				        		.accountName("saving account")
//				        		.shortCode(scbUsers.getUserID())
//				        		.build();
//										        
//				        //save to db
//				        iRaiBankAccountsRepository.save(bankAccounts1);
//				        log.debug("Insert RAI_BANK_ACCOUNTS ,Account No : "+bankAccounts1.getAccountNo());
//				        
//						//insert bank account 2
//				        BankAccounts bankAccounts2 = new BankAccounts().builder()
//				        		.users(users)
//				        		.banks(raiBanks2)
//				        		.isDefault(false)
//				        		.balance(balance)
//				        		.currency("GBP")
//				        		.reservedBalance(new BigDecimal(0))
//				        		.accountNo(bankAcctNo2)
//				        		.accountName("retirement account")
//				        		.shortCode(scbUsers.getUserID())
//				        		.build();
//										        
//				        //save to db
//				        iRaiBankAccountsRepository.save(bankAccounts2);
//				        log.debug("Insert RAI_BANK_ACCOUNTS ,Account No : "+bankAccounts2.getAccountNo());
//				        newRaiUsersList.add(scbUsers);
//					}else {
//						
//						log.debug("User ID : "+scbUsers.getUserID()+" already exist");
//					}
//				}
//			}else {
//				log.info("Bank not found");
//			}
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return newRaiUsersList;
//	}
}
