package dcsiclear.thread;

public class IBMMQCfg {
	
	private String queueManager;

	private String channel;

	private String queueName;

	private int port;

	private int ccsid;

	private String hostName;
	
	private String clientId;
	
	private int targetClient;
	
	private int encoding;
	
	private int maxdepth;
	
	private String charsetEncoding;
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getCcsid() {
		return ccsid;
	}

	public void setCcsid(int ccsid) {
		this.ccsid = ccsid;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getQueueManager() {
		return queueManager;
	}

	public void setQueueManager(String queueManager) {
		this.queueManager = queueManager;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public int getTargetClient() {
		return targetClient;
	}

	public void setTargetClient(int targetClient) {
		this.targetClient = targetClient;
	}

	public int getEncoding() {
		return encoding;
	}

	public void setEncoding(int encoding) {
		this.encoding = encoding;
	}

	public int getMaxdepth() {
		return maxdepth;
	}

	public void setMaxdepth(int maxdepth) {
		this.maxdepth = maxdepth;
	}

	public String getCharsetEncoding() {
		return charsetEncoding;
	}

	public void setCharsetEncoding(String charsetEncoding) {
		this.charsetEncoding = charsetEncoding;
	}

}
