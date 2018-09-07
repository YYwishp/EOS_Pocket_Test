package com.lifemenu.eos_pocket_test.blockchain.bean;

import java.util.List;

/**
 * Created by gyx on 2018/7/12.
 */
public class TableResultBean {
	/**
	 * rows : [{"supply":"10000000000.0000 RAMCORE","base":{"balance":"68678368185 RAM","weight":"0.50000000000000000"},"quote":{"balance":"1000598.5664 EOS","weight":"0.50000000000000000"}}]
	 * more : false
	 */
	private boolean more;
	private List<RowsBean> rows;

	public boolean isMore() {
		return more;
	}

	public void setMore(boolean more) {
		this.more = more;
	}

	public List<RowsBean> getRows() {
		return rows;
	}

	public void setRows(List<RowsBean> rows) {
		this.rows = rows;
	}

	public static class RowsBean {
		/**
		 * supply : 10000000000.0000 RAMCORE
		 * base : {"balance":"68678368185 RAM","weight":"0.50000000000000000"}
		 * quote : {"balance":"1000598.5664 EOS","weight":"0.50000000000000000"}
		 */
		private String supply;
		private BaseBean base;
		private QuoteBean quote;

		public String getSupply() {
			return supply;
		}

		public void setSupply(String supply) {
			this.supply = supply;
		}

		public BaseBean getBase() {
			return base;
		}

		public void setBase(BaseBean base) {
			this.base = base;
		}

		public QuoteBean getQuote() {
			return quote;
		}

		public void setQuote(QuoteBean quote) {
			this.quote = quote;
		}

		public static class BaseBean {
			/**
			 * balance : 68678368185 RAM
			 * weight : 0.50000000000000000
			 */
			private String balance;
			private String weight;

			public String getBalance() {
				return balance;
			}

			public void setBalance(String balance) {
				this.balance = balance;
			}

			public String getWeight() {
				return weight;
			}

			public void setWeight(String weight) {
				this.weight = weight;
			}
		}

		public static class QuoteBean {
			/**
			 * balance : 1000598.5664 EOS
			 * weight : 0.50000000000000000
			 */
			private String balance;
			private String weight;

			public String getBalance() {
				return balance;
			}

			public void setBalance(String balance) {
				this.balance = balance;
			}

			public String getWeight() {
				return weight;
			}

			public void setWeight(String weight) {
				this.weight = weight;
			}
		}
	}
}
