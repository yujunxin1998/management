package com.yjx.common.web.exception;

import com.yjx.common.base.response.IResponseCode;
import lombok.Getter;

@Getter
public class BizBaseException extends RuntimeException{

   private IResponseCode responseCode;


   public BizBaseException(IResponseCode responseCode) {
      super(responseCode.getMsg());
      this.responseCode = responseCode;
   }

   public BizBaseException(String message) {
      super(message);
   }

   public BizBaseException(String message,Throwable cause){
      super(message,cause);
   }

   public BizBaseException(Throwable cause) {
      super(cause);
   }
}
