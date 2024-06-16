package com.airfrance.ref.exception;

public class ContractExistException extends  RefException {

		private static final long serialVersionUID = -1432337699035001046L;

		private static final String ERROR_MESSAGE = "A contract with the same number already exists";
		
		public ContractExistException(String msg) {
			super(ERROR_MESSAGE, msg);
		}

		public ContractExistException(Throwable root) {
			super(root);
		}

		public ContractExistException(String msg, Throwable root) {
			super(msg, root);
		}

}
