/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package SignUp;

import java.util.UUID;

/**
 *
 * @author ALI
 */
public class VerificationCode {
    private String code;

    public VerificationCode() {
        generateVerificationCode();
    }
    
    private void generateVerificationCode(){
        code = UUID.randomUUID().toString();
    }

    public String getCode() {
        return code;
    }
}
