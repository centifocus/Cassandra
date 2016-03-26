package wangliqiu.test.common;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;

public class JustTest {
	protected static Logger logger = Logger.getLogger(JustTest.class);
	String sourceHostName = "";
	String sourceChannel = "";
	int sourcePort = 1414;
	int waitInterval = 6000;
	String sourceMqManager = "";
	String sourceMqName = "";
	String targetHostName = "";
	String targetChannel = "";
	int targetPort = 1414;
	String targetMqManager = "";

	@Before
	public void init() {

	}

	@Test
	public void execute() throws IOException, InterruptedException, MQException {
		/*
		 * the value of the key TRANSPORT_PROPERTY must be one of MQC.TRANSPORT_MQSERIES_BINDINGS or
		 * MQC.TRANSPORT_MQSERIES_CLIENT. The default is MQC.TRANSPORT_MQSERIES, which selects a
		 * transport based on the value of the hostname property.
		 */
		// MQEnvironment.properties.put(MQConstants.TRANSPORT_PROPERTY,
		// MQConstants.TRANSPORT_MQSERIES);
		MQEnvironment.CCSID = 1208;
		MQEnvironment.hostname = sourceHostName;
		MQEnvironment.channel = sourceChannel;
		MQEnvironment.port = sourcePort;
		/* This class contains options which control the behaviour of MQQueue.get(). */
		MQGetMessageOptions getMsgOptions = new MQGetMessageOptions();
		/*
		 * MQGMO_SYNCPOINT: The request is to operate within the normal unit-of-work protocols. The
		 * message is marked as being unavailable to other applications, but it is deleted from the
		 * queue only when the unit of work is committed. The message is made available again if the
		 * unit of work is backed out.
		 */
		/*
		 * MQGMO_WAIT: The application waits until a suitable message arrives. The maximum time that
		 * the application waits is specified in WaitInterval.
		 */
		/*
		 * MQGMO_FAIL_IF_QUIESCING: Force the MQGET call to fail if the queue manager is in the
		 * quiescing state.
		 */
		getMsgOptions.options = MQConstants.MQGMO_SYNCPOINT | MQConstants.MQGMO_WAIT
				| MQConstants.MQGMO_FAIL_IF_QUIESCING;
		getMsgOptions.waitInterval = waitInterval;

		MQQueueManager mqMgr = new MQQueueManager(sourceMqManager);
		/*
		 * The queue is opened for use with subsequent MQGET calls. The call can succeed if the
		 * queue is currently open by this or another application with MQOO_INPUT_SHARED, but fails
		 * with reason code MQRC_OBJECT_IN_USE if the queue is currently open with
		 * MQOO_INPUT_EXCLUSIVE.
		 */
		MQQueue mqQueue = mqMgr.accessQueue(sourceMqName, MQConstants.MQOO_INPUT_SHARED
				| MQConstants.MQOO_FAIL_IF_QUIESCING);
		MQMessage mqMsg = new MQMessage();
		mqQueue.get(mqMsg, getMsgOptions);
		/*
		 * Indicates to the queue manager that the application has reached a syncpoint. Messages
		 * sent as part of a unit of work (with the MQC.MQPMO_SYNCPOINT flag set in
		 * MQPutMessageOptions.options) are made available to other applications. Messages retrieved
		 * as part of a unit of work (with the MQC.MQGMO_SYNCPOINT flag set in
		 * MQGetMessageOptions.options) are deleted.
		 */
		mqMgr.commit();

		byte[] xmlByte = new byte[mqMsg.getMessageLength()];
		mqMsg.readFully(xmlByte);
		String xmlMessage = new String(xmlByte, "utf-8");

		/**
		 * target
		 */
		MQEnvironment.CCSID = mqMsg.characterSet;
		MQEnvironment.hostname = targetHostName;
		MQEnvironment.channel = targetChannel;
		MQEnvironment.port = targetPort;

		MQQueueManager mqMgrReply = new MQQueueManager(targetMqManager);
		/*
		 * MQOO_OUTPUT: Open the queue to put messages. The queue is opened for use with subsequent
		 * MQPUT calls.
		 */
		MQQueue mqQueueReply = mqMgrReply.accessQueue(mqMsg.replyToQueueName, MQConstants.MQOO_OUTPUT
				| MQConstants.MQOO_FAIL_IF_QUIESCING, mqMsg.replyToQueueManagerName, null, null);

		MQPutMessageOptions mpmo = new MQPutMessageOptions();
		MQMessage responseMQMsg = new MQMessage();
		responseMQMsg.characterSet = mqMsg.characterSet;
		responseMQMsg.correlationId = mqMsg.messageId;

		responseMQMsg.writeString(xmlMessage);

		mqQueueReply.put(responseMQMsg, mpmo);

		mqQueue.close();
		mqMgr.close();
		mqMgr.disconnect();
	}
}
