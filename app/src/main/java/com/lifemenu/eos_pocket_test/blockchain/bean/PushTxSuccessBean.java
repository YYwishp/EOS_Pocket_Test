package com.lifemenu.eos_pocket_test.blockchain.bean;

import java.util.List;

/**
 * Created by gyx on 2018/7/12.
 */
public class PushTxSuccessBean {
	/**
	 * transaction_id : ab71c996c4d41119fa397dc3506d1441f2d33d281f27c45771dc78f6eff60de6
	 * processed : {"id":"ab71c996c4d41119fa397dc3506d1441f2d33d281f27c45771dc78f6eff60de6","receipt":{"status":"executed","cpu_usage_us":843,"net_usage_words":18},"elapsed":843,"net_usage":144,"scheduled":false,"action_traces":[{"receipt":{"receiver":"eosio.token","act_digest":"c3d59e22db1d31834a20f39e9e4fcb0430acd81186c839b6063203e3ec290a69","global_sequence":1186719,"recv_sequence":71,"auth_sequence":[["useraaaaaaab",65]],"code_sequence":1,"abi_sequence":1},"act":{"account":"eosio.token","name":"transfer","authorization":[{"actor":"useraaaaaaab","permission":"active"}],"data":{"from":"useraaaaaaab","to":"useraaaaaaac","quantity":"8.0000 EOS","memo":"大吉大利"},"hex_data":"708c31c6187315d6808c31c6187315d6803801000000000004454f53000000000ce5a4a7e59089e5a4a7e588a9"},"elapsed":569,"cpu_usage":0,"console":"","total_cpu_usage":0,"trx_id":"ab71c996c4d41119fa397dc3506d1441f2d33d281f27c45771dc78f6eff60de6","inline_traces":[{"receipt":{"receiver":"useraaaaaaab","act_digest":"c3d59e22db1d31834a20f39e9e4fcb0430acd81186c839b6063203e3ec290a69","global_sequence":1186720,"recv_sequence":21,"auth_sequence":[["useraaaaaaab",66]],"code_sequence":1,"abi_sequence":1},"act":{"account":"eosio.token","name":"transfer","authorization":[{"actor":"useraaaaaaab","permission":"active"}],"data":{"from":"useraaaaaaab","to":"useraaaaaaac","quantity":"8.0000 EOS","memo":"大吉大利"},"hex_data":"708c31c6187315d6808c31c6187315d6803801000000000004454f53000000000ce5a4a7e59089e5a4a7e588a9"},"elapsed":4,"cpu_usage":0,"console":"","total_cpu_usage":0,"trx_id":"ab71c996c4d41119fa397dc3506d1441f2d33d281f27c45771dc78f6eff60de6","inline_traces":[]},{"receipt":{"receiver":"useraaaaaaac","act_digest":"c3d59e22db1d31834a20f39e9e4fcb0430acd81186c839b6063203e3ec290a69","global_sequence":1186721,"recv_sequence":10,"auth_sequence":[["useraaaaaaab",67]],"code_sequence":1,"abi_sequence":1},"act":{"account":"eosio.token","name":"transfer","authorization":[{"actor":"useraaaaaaab","permission":"active"}],"data":{"from":"useraaaaaaab","to":"useraaaaaaac","quantity":"8.0000 EOS","memo":"大吉大利"},"hex_data":"708c31c6187315d6808c31c6187315d6803801000000000004454f53000000000ce5a4a7e59089e5a4a7e588a9"},"elapsed":3,"cpu_usage":0,"console":"","total_cpu_usage":0,"trx_id":"ab71c996c4d41119fa397dc3506d1441f2d33d281f27c45771dc78f6eff60de6","inline_traces":[]}]}],"except":null}
	 */
	private String transaction_id;
	private ProcessedBean processed;

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public ProcessedBean getProcessed() {
		return processed;
	}

	public void setProcessed(ProcessedBean processed) {
		this.processed = processed;
	}

	public static class ProcessedBean {
		/**
		 * id : ab71c996c4d41119fa397dc3506d1441f2d33d281f27c45771dc78f6eff60de6
		 * receipt : {"status":"executed","cpu_usage_us":843,"net_usage_words":18}
		 * elapsed : 843
		 * net_usage : 144
		 * scheduled : false
		 * action_traces : [{"receipt":{"receiver":"eosio.token","act_digest":"c3d59e22db1d31834a20f39e9e4fcb0430acd81186c839b6063203e3ec290a69","global_sequence":1186719,"recv_sequence":71,"auth_sequence":[["useraaaaaaab",65]],"code_sequence":1,"abi_sequence":1},"act":{"account":"eosio.token","name":"transfer","authorization":[{"actor":"useraaaaaaab","permission":"active"}],"data":{"from":"useraaaaaaab","to":"useraaaaaaac","quantity":"8.0000 EOS","memo":"大吉大利"},"hex_data":"708c31c6187315d6808c31c6187315d6803801000000000004454f53000000000ce5a4a7e59089e5a4a7e588a9"},"elapsed":569,"cpu_usage":0,"console":"","total_cpu_usage":0,"trx_id":"ab71c996c4d41119fa397dc3506d1441f2d33d281f27c45771dc78f6eff60de6","inline_traces":[{"receipt":{"receiver":"useraaaaaaab","act_digest":"c3d59e22db1d31834a20f39e9e4fcb0430acd81186c839b6063203e3ec290a69","global_sequence":1186720,"recv_sequence":21,"auth_sequence":[["useraaaaaaab",66]],"code_sequence":1,"abi_sequence":1},"act":{"account":"eosio.token","name":"transfer","authorization":[{"actor":"useraaaaaaab","permission":"active"}],"data":{"from":"useraaaaaaab","to":"useraaaaaaac","quantity":"8.0000 EOS","memo":"大吉大利"},"hex_data":"708c31c6187315d6808c31c6187315d6803801000000000004454f53000000000ce5a4a7e59089e5a4a7e588a9"},"elapsed":4,"cpu_usage":0,"console":"","total_cpu_usage":0,"trx_id":"ab71c996c4d41119fa397dc3506d1441f2d33d281f27c45771dc78f6eff60de6","inline_traces":[]},{"receipt":{"receiver":"useraaaaaaac","act_digest":"c3d59e22db1d31834a20f39e9e4fcb0430acd81186c839b6063203e3ec290a69","global_sequence":1186721,"recv_sequence":10,"auth_sequence":[["useraaaaaaab",67]],"code_sequence":1,"abi_sequence":1},"act":{"account":"eosio.token","name":"transfer","authorization":[{"actor":"useraaaaaaab","permission":"active"}],"data":{"from":"useraaaaaaab","to":"useraaaaaaac","quantity":"8.0000 EOS","memo":"大吉大利"},"hex_data":"708c31c6187315d6808c31c6187315d6803801000000000004454f53000000000ce5a4a7e59089e5a4a7e588a9"},"elapsed":3,"cpu_usage":0,"console":"","total_cpu_usage":0,"trx_id":"ab71c996c4d41119fa397dc3506d1441f2d33d281f27c45771dc78f6eff60de6","inline_traces":[]}]}]
		 * except : null
		 */
		private String id;
		private ReceiptBean receipt;
		private int elapsed;
		private int net_usage;
		private boolean scheduled;
		private Object except;
		private List<ActionTracesBean> action_traces;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public ReceiptBean getReceipt() {
			return receipt;
		}

		public void setReceipt(ReceiptBean receipt) {
			this.receipt = receipt;
		}

		public int getElapsed() {
			return elapsed;
		}

		public void setElapsed(int elapsed) {
			this.elapsed = elapsed;
		}

		public int getNet_usage() {
			return net_usage;
		}

		public void setNet_usage(int net_usage) {
			this.net_usage = net_usage;
		}

		public boolean isScheduled() {
			return scheduled;
		}

		public void setScheduled(boolean scheduled) {
			this.scheduled = scheduled;
		}

		public Object getExcept() {
			return except;
		}

		public void setExcept(Object except) {
			this.except = except;
		}

		public List<ActionTracesBean> getAction_traces() {
			return action_traces;
		}

		public void setAction_traces(List<ActionTracesBean> action_traces) {
			this.action_traces = action_traces;
		}

		public static class ReceiptBean {
			/**
			 * status : executed
			 * cpu_usage_us : 843
			 * net_usage_words : 18
			 */
			private String status;
			private int cpu_usage_us;
			private int net_usage_words;

			public String getStatus() {
				return status;
			}

			public void setStatus(String status) {
				this.status = status;
			}

			public int getCpu_usage_us() {
				return cpu_usage_us;
			}

			public void setCpu_usage_us(int cpu_usage_us) {
				this.cpu_usage_us = cpu_usage_us;
			}

			public int getNet_usage_words() {
				return net_usage_words;
			}

			public void setNet_usage_words(int net_usage_words) {
				this.net_usage_words = net_usage_words;
			}
		}

		public static class ActionTracesBean {
			/**
			 * receipt : {"receiver":"eosio.token","act_digest":"c3d59e22db1d31834a20f39e9e4fcb0430acd81186c839b6063203e3ec290a69","global_sequence":1186719,"recv_sequence":71,"auth_sequence":[["useraaaaaaab",65]],"code_sequence":1,"abi_sequence":1}
			 * act : {"account":"eosio.token","name":"transfer","authorization":[{"actor":"useraaaaaaab","permission":"active"}],"data":{"from":"useraaaaaaab","to":"useraaaaaaac","quantity":"8.0000 EOS","memo":"大吉大利"},"hex_data":"708c31c6187315d6808c31c6187315d6803801000000000004454f53000000000ce5a4a7e59089e5a4a7e588a9"}
			 * elapsed : 569
			 * cpu_usage : 0
			 * console :
			 * total_cpu_usage : 0
			 * trx_id : ab71c996c4d41119fa397dc3506d1441f2d33d281f27c45771dc78f6eff60de6
			 * inline_traces : [{"receipt":{"receiver":"useraaaaaaab","act_digest":"c3d59e22db1d31834a20f39e9e4fcb0430acd81186c839b6063203e3ec290a69","global_sequence":1186720,"recv_sequence":21,"auth_sequence":[["useraaaaaaab",66]],"code_sequence":1,"abi_sequence":1},"act":{"account":"eosio.token","name":"transfer","authorization":[{"actor":"useraaaaaaab","permission":"active"}],"data":{"from":"useraaaaaaab","to":"useraaaaaaac","quantity":"8.0000 EOS","memo":"大吉大利"},"hex_data":"708c31c6187315d6808c31c6187315d6803801000000000004454f53000000000ce5a4a7e59089e5a4a7e588a9"},"elapsed":4,"cpu_usage":0,"console":"","total_cpu_usage":0,"trx_id":"ab71c996c4d41119fa397dc3506d1441f2d33d281f27c45771dc78f6eff60de6","inline_traces":[]},{"receipt":{"receiver":"useraaaaaaac","act_digest":"c3d59e22db1d31834a20f39e9e4fcb0430acd81186c839b6063203e3ec290a69","global_sequence":1186721,"recv_sequence":10,"auth_sequence":[["useraaaaaaab",67]],"code_sequence":1,"abi_sequence":1},"act":{"account":"eosio.token","name":"transfer","authorization":[{"actor":"useraaaaaaab","permission":"active"}],"data":{"from":"useraaaaaaab","to":"useraaaaaaac","quantity":"8.0000 EOS","memo":"大吉大利"},"hex_data":"708c31c6187315d6808c31c6187315d6803801000000000004454f53000000000ce5a4a7e59089e5a4a7e588a9"},"elapsed":3,"cpu_usage":0,"console":"","total_cpu_usage":0,"trx_id":"ab71c996c4d41119fa397dc3506d1441f2d33d281f27c45771dc78f6eff60de6","inline_traces":[]}]
			 */
			private ReceiptBeanX receipt;
			private ActBean act;
			private int elapsed;
			private int cpu_usage;
			private String console;
			private int total_cpu_usage;
			private String trx_id;
			private List<InlineTracesBean> inline_traces;

			public ReceiptBeanX getReceipt() {
				return receipt;
			}

			public void setReceipt(ReceiptBeanX receipt) {
				this.receipt = receipt;
			}

			public ActBean getAct() {
				return act;
			}

			public void setAct(ActBean act) {
				this.act = act;
			}

			public int getElapsed() {
				return elapsed;
			}

			public void setElapsed(int elapsed) {
				this.elapsed = elapsed;
			}

			public int getCpu_usage() {
				return cpu_usage;
			}

			public void setCpu_usage(int cpu_usage) {
				this.cpu_usage = cpu_usage;
			}

			public String getConsole() {
				return console;
			}

			public void setConsole(String console) {
				this.console = console;
			}

			public int getTotal_cpu_usage() {
				return total_cpu_usage;
			}

			public void setTotal_cpu_usage(int total_cpu_usage) {
				this.total_cpu_usage = total_cpu_usage;
			}

			public String getTrx_id() {
				return trx_id;
			}

			public void setTrx_id(String trx_id) {
				this.trx_id = trx_id;
			}

			public List<InlineTracesBean> getInline_traces() {
				return inline_traces;
			}

			public void setInline_traces(List<InlineTracesBean> inline_traces) {
				this.inline_traces = inline_traces;
			}

			public static class ReceiptBeanX {
				/**
				 * receiver : eosio.token
				 * act_digest : c3d59e22db1d31834a20f39e9e4fcb0430acd81186c839b6063203e3ec290a69
				 * global_sequence : 1186719
				 * recv_sequence : 71
				 * auth_sequence : [["useraaaaaaab",65]]
				 * code_sequence : 1
				 * abi_sequence : 1
				 */
				private String receiver;
				private String act_digest;
				private int global_sequence;
				private int recv_sequence;
				private int code_sequence;
				private int abi_sequence;
				private List<List<String>> auth_sequence;

				public String getReceiver() {
					return receiver;
				}

				public void setReceiver(String receiver) {
					this.receiver = receiver;
				}

				public String getAct_digest() {
					return act_digest;
				}

				public void setAct_digest(String act_digest) {
					this.act_digest = act_digest;
				}

				public int getGlobal_sequence() {
					return global_sequence;
				}

				public void setGlobal_sequence(int global_sequence) {
					this.global_sequence = global_sequence;
				}

				public int getRecv_sequence() {
					return recv_sequence;
				}

				public void setRecv_sequence(int recv_sequence) {
					this.recv_sequence = recv_sequence;
				}

				public int getCode_sequence() {
					return code_sequence;
				}

				public void setCode_sequence(int code_sequence) {
					this.code_sequence = code_sequence;
				}

				public int getAbi_sequence() {
					return abi_sequence;
				}

				public void setAbi_sequence(int abi_sequence) {
					this.abi_sequence = abi_sequence;
				}

				public List<List<String>> getAuth_sequence() {
					return auth_sequence;
				}

				public void setAuth_sequence(List<List<String>> auth_sequence) {
					this.auth_sequence = auth_sequence;
				}
			}

			public static class ActBean {
				/**
				 * account : eosio.token
				 * name : transfer
				 * authorization : [{"actor":"useraaaaaaab","permission":"active"}]
				 * data : {"from":"useraaaaaaab","to":"useraaaaaaac","quantity":"8.0000 EOS","memo":"大吉大利"}
				 * hex_data : 708c31c6187315d6808c31c6187315d6803801000000000004454f53000000000ce5a4a7e59089e5a4a7e588a9
				 */
				private String account;
				private String name;
				private DataBean data;
				private String hex_data;
				private List<AuthorizationBean> authorization;

				public String getAccount() {
					return account;
				}

				public void setAccount(String account) {
					this.account = account;
				}

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public DataBean getData() {
					return data;
				}

				public void setData(DataBean data) {
					this.data = data;
				}

				public String getHex_data() {
					return hex_data;
				}

				public void setHex_data(String hex_data) {
					this.hex_data = hex_data;
				}

				public List<AuthorizationBean> getAuthorization() {
					return authorization;
				}

				public void setAuthorization(List<AuthorizationBean> authorization) {
					this.authorization = authorization;
				}

				public static class DataBean {
					/**
					 * from : useraaaaaaab
					 * to : useraaaaaaac
					 * quantity : 8.0000 EOS
					 * memo : 大吉大利
					 */
					private String from;
					private String to;
					private String quantity;
					private String memo;

					public String getFrom() {
						return from;
					}

					public void setFrom(String from) {
						this.from = from;
					}

					public String getTo() {
						return to;
					}

					public void setTo(String to) {
						this.to = to;
					}

					public String getQuantity() {
						return quantity;
					}

					public void setQuantity(String quantity) {
						this.quantity = quantity;
					}

					public String getMemo() {
						return memo;
					}

					public void setMemo(String memo) {
						this.memo = memo;
					}
				}

				public static class AuthorizationBean {
					/**
					 * actor : useraaaaaaab
					 * permission : active
					 */
					private String actor;
					private String permission;

					public String getActor() {
						return actor;
					}

					public void setActor(String actor) {
						this.actor = actor;
					}

					public String getPermission() {
						return permission;
					}

					public void setPermission(String permission) {
						this.permission = permission;
					}
				}
			}

			public static class InlineTracesBean {
				/**
				 * receipt : {"receiver":"useraaaaaaab","act_digest":"c3d59e22db1d31834a20f39e9e4fcb0430acd81186c839b6063203e3ec290a69","global_sequence":1186720,"recv_sequence":21,"auth_sequence":[["useraaaaaaab",66]],"code_sequence":1,"abi_sequence":1}
				 * act : {"account":"eosio.token","name":"transfer","authorization":[{"actor":"useraaaaaaab","permission":"active"}],"data":{"from":"useraaaaaaab","to":"useraaaaaaac","quantity":"8.0000 EOS","memo":"大吉大利"},"hex_data":"708c31c6187315d6808c31c6187315d6803801000000000004454f53000000000ce5a4a7e59089e5a4a7e588a9"}
				 * elapsed : 4
				 * cpu_usage : 0
				 * console :
				 * total_cpu_usage : 0
				 * trx_id : ab71c996c4d41119fa397dc3506d1441f2d33d281f27c45771dc78f6eff60de6
				 * inline_traces : []
				 */
				private ReceiptBeanXX receipt;
				private ActBeanX act;
				private int elapsed;
				private int cpu_usage;
				private String console;
				private int total_cpu_usage;
				private String trx_id;
				private List<?> inline_traces;

				public ReceiptBeanXX getReceipt() {
					return receipt;
				}

				public void setReceipt(ReceiptBeanXX receipt) {
					this.receipt = receipt;
				}

				public ActBeanX getAct() {
					return act;
				}

				public void setAct(ActBeanX act) {
					this.act = act;
				}

				public int getElapsed() {
					return elapsed;
				}

				public void setElapsed(int elapsed) {
					this.elapsed = elapsed;
				}

				public int getCpu_usage() {
					return cpu_usage;
				}

				public void setCpu_usage(int cpu_usage) {
					this.cpu_usage = cpu_usage;
				}

				public String getConsole() {
					return console;
				}

				public void setConsole(String console) {
					this.console = console;
				}

				public int getTotal_cpu_usage() {
					return total_cpu_usage;
				}

				public void setTotal_cpu_usage(int total_cpu_usage) {
					this.total_cpu_usage = total_cpu_usage;
				}

				public String getTrx_id() {
					return trx_id;
				}

				public void setTrx_id(String trx_id) {
					this.trx_id = trx_id;
				}

				public List<?> getInline_traces() {
					return inline_traces;
				}

				public void setInline_traces(List<?> inline_traces) {
					this.inline_traces = inline_traces;
				}

				public static class ReceiptBeanXX {
					/**
					 * receiver : useraaaaaaab
					 * act_digest : c3d59e22db1d31834a20f39e9e4fcb0430acd81186c839b6063203e3ec290a69
					 * global_sequence : 1186720
					 * recv_sequence : 21
					 * auth_sequence : [["useraaaaaaab",66]]
					 * code_sequence : 1
					 * abi_sequence : 1
					 */
					private String receiver;
					private String act_digest;
					private int global_sequence;
					private int recv_sequence;
					private int code_sequence;
					private int abi_sequence;
					private List<List<String>> auth_sequence;

					public String getReceiver() {
						return receiver;
					}

					public void setReceiver(String receiver) {
						this.receiver = receiver;
					}

					public String getAct_digest() {
						return act_digest;
					}

					public void setAct_digest(String act_digest) {
						this.act_digest = act_digest;
					}

					public int getGlobal_sequence() {
						return global_sequence;
					}

					public void setGlobal_sequence(int global_sequence) {
						this.global_sequence = global_sequence;
					}

					public int getRecv_sequence() {
						return recv_sequence;
					}

					public void setRecv_sequence(int recv_sequence) {
						this.recv_sequence = recv_sequence;
					}

					public int getCode_sequence() {
						return code_sequence;
					}

					public void setCode_sequence(int code_sequence) {
						this.code_sequence = code_sequence;
					}

					public int getAbi_sequence() {
						return abi_sequence;
					}

					public void setAbi_sequence(int abi_sequence) {
						this.abi_sequence = abi_sequence;
					}

					public List<List<String>> getAuth_sequence() {
						return auth_sequence;
					}

					public void setAuth_sequence(List<List<String>> auth_sequence) {
						this.auth_sequence = auth_sequence;
					}
				}

				public static class ActBeanX {
					/**
					 * account : eosio.token
					 * name : transfer
					 * authorization : [{"actor":"useraaaaaaab","permission":"active"}]
					 * data : {"from":"useraaaaaaab","to":"useraaaaaaac","quantity":"8.0000 EOS","memo":"大吉大利"}
					 * hex_data : 708c31c6187315d6808c31c6187315d6803801000000000004454f53000000000ce5a4a7e59089e5a4a7e588a9
					 */
					private String account;
					private String name;
					private DataBeanX data;
					private String hex_data;
					private List<AuthorizationBeanX> authorization;

					public String getAccount() {
						return account;
					}

					public void setAccount(String account) {
						this.account = account;
					}

					public String getName() {
						return name;
					}

					public void setName(String name) {
						this.name = name;
					}

					public DataBeanX getData() {
						return data;
					}

					public void setData(DataBeanX data) {
						this.data = data;
					}

					public String getHex_data() {
						return hex_data;
					}

					public void setHex_data(String hex_data) {
						this.hex_data = hex_data;
					}

					public List<AuthorizationBeanX> getAuthorization() {
						return authorization;
					}

					public void setAuthorization(List<AuthorizationBeanX> authorization) {
						this.authorization = authorization;
					}

					public static class DataBeanX {
						/**
						 * from : useraaaaaaab
						 * to : useraaaaaaac
						 * quantity : 8.0000 EOS
						 * memo : 大吉大利
						 */
						private String from;
						private String to;
						private String quantity;
						private String memo;

						public String getFrom() {
							return from;
						}

						public void setFrom(String from) {
							this.from = from;
						}

						public String getTo() {
							return to;
						}

						public void setTo(String to) {
							this.to = to;
						}

						public String getQuantity() {
							return quantity;
						}

						public void setQuantity(String quantity) {
							this.quantity = quantity;
						}

						public String getMemo() {
							return memo;
						}

						public void setMemo(String memo) {
							this.memo = memo;
						}
					}

					public static class AuthorizationBeanX {
						/**
						 * actor : useraaaaaaab
						 * permission : active
						 */
						private String actor;
						private String permission;

						public String getActor() {
							return actor;
						}

						public void setActor(String actor) {
							this.actor = actor;
						}

						public String getPermission() {
							return permission;
						}

						public void setPermission(String permission) {
							this.permission = permission;
						}
					}
				}
			}
		}
	}
}
