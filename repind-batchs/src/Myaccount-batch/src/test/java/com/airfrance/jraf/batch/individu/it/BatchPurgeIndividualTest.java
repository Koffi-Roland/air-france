package com.airfrance.jraf.batch.individu.it;

import com.afklm.soa.stubs.common.systemfault.v1.SystemException;
import com.afklm.soa.stubs.w001580.v3.ProvideCustomer360ViewBusinessException;
import com.afklm.soa.stubs.w001580.v3.ProvideCustomer360ViewV3;
import com.afklm.soa.stubs.w001580.v3.cbs.*;
import com.afklm.soa.stubs.w001580.v3.commons.CrmProfileData;
import com.afklm.soa.stubs.w001580.v3.commons.CustomerCase;
import com.afklm.soa.stubs.w001580.v3.commons.CustomerCaseData;
import com.afklm.soa.stubs.w001580.v3.commons.ProfilingData;
import com.airfrance.jraf.batch.config.WebConfigTestBatch;
import com.airfrance.jraf.batch.individu.BatchPurgeIndividual;
import com.airfrance.repind.service.individu.internal.PurgeIndividualDS;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;



@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebConfigTestBatch.class)
@Transactional(value = "transactionManagerRepind")
public class BatchPurgeIndividualTest {

	private static final long DEFAUTL_DELAY_CBS_360 = 100;

	@Autowired
	private ProvideCustomer360ViewV3 provideCustomer360ViewV3;
	
	@PersistenceContext(unitName = "entityManagerFactoryRepind")
	private EntityManager entityManager;
	
	@Autowired
    private ApplicationContext context;
	
	@Ignore
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void executeTest() throws Throwable {
		BatchPurgeIndividual.main(new String[] { "" });
	}

	@Ignore
	@Category(com.airfrance.repind.util.TestCategory.Slow.class)
	public void executeWithParametersTest() throws Throwable {
		BatchPurgeIndividual.main(new String[] { "--number-database-cpu-core", "8", "--number-database-pool", "12" });
	}
	
	
	@SuppressWarnings("unused")
	@Test(expected = Test.None.class /* no exception expected */)
	@Ignore
	public void testProvideCustomer360View_CBS() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	//List<String> tmp = Arrays.asList("910046772433", "910046772444", "110000014901", "910046772455", "910046772466", "110000017001", "110000017701", "110000018401", "910046772470", "110000019801", "910046772481", "910046772492", "910046772503", "910046772514", "110000023301", "910046772525", "910046772536", "110000025401", "910046772540", "910046772551", "910046772562", "910046772573", "110000028901", "110000029601", "110000030301", "910046772584", "910046772595", "910046772606", "910046772610", "910046772621", "910046772632", "910046772643", "910046772654", "910046772665", "110000038001", "110000038701", "110000039401", "110000040101", "110000040801", "110000043601", "110000044301", "110000045701", "110000046401", "110000047101", "110000050601", "110000053401", "110000054101", "110000055501", "110000056901", "110000057601", "110000058301", "110000059001", "110000061801", "110000062501", "110000063901", "110000064601", "110000066001", "110000070901", "110000071601", "110000072301", "110000073001", "110000075801", "110000076501", "110000077201", "110000080701", "110000081401", "110000082101", "110000083501", "110000084201", "110000085601", "110000086301", "110000087001", "110000087701", "110000088401", "110000089101", "110000089801", "110000091201", "110000091901", "110000093301", "110000096801", "400272036075", "110000101001", "400272036086", "110000103801", "400272036101", "110000106601", "110000107301", "400272036112", "400272036156", "400272036160", "400272036182", "400272036193", "400272036215", "400272036252", "400272036274", "110000013711", "110000122701", "400272036296", "400272036307", "400476875605", "400476875616", "400476875620", "110000015811", "400476875664", "400476875690", "400476875734", "110000019311", "400476875756", "400476875771", "110000021411", "400476875793", "110000022111", "400476875804", "400476875815", "400476875841", "400476875852", "110000023511", "110000024911", "110000025611", "400272036322", "400272036333", "400272036355", "400272036366", "110000030511", "400272036381", "110000149301", "110000150001", "110000150701", "400272036403", "400272036436", "110000034011", "400272036440", "110000153501", "110000034711", "400272036462", "400272036473", "400272036495", "400272036521", "400272036532", "110000038911", "400462465291", "400228259162", "110000041011", "400462465335", "400228259184", "400462465350", "400462465361", "400228259276", "400462465383", "400462465394", "400228259302", "400462465416", "400462465420", "400228259372", "400228259394", "400228259405", "400462465442", "110000176601", "400462465486", "400228259464", "400228259475", "400272036845", "400272036882", "400272036904", "400272036930", "400272036952", "110000186401", "110000187101", "400272036974", "400272036985", "110000187801", "110000053611", "110000055011", "400272037011", "400272037033", "400272037044", "400272037055", "400272037066", "400272037077", "400272037092", "110000059211", "110000059911", "110000060611", "110000061311", "110000062011", "110000198301", "400272036580", "110000063411", "400272036635", "400272036650", "400272036661", "400272036672", "400272036683", "110000204601", "110000066211", "400272036705", "110000208101", "400272036742", "400272036764", "400272036790", "400272036801", "400272036812", "400272036834", "110000071111", "110000214401", "400476882616", "110000073211", "400476882620", "110000076011", "110000076711", "400476882653", "400476882664", "110000078811", "400476882675", "110000080211", "400476882712", "400476882734", "110000081611", "400476882745", "400476882782", "110000082311", "110000222101", "110000083011", "400476882804", "110000224201", "110000224901", "400476882815", "400476882826", "400476882830", "110000226301", "400476882852", "110000085111", "110000087911", "110000088611", "110000089311", "110000090011", "110000090711", "110000091411", "110000231201", "110000233301", "110000234001", "110000234701", "110000092811", "110000236801", "110000013921", "110000094911", "110000095611", "110000096311", "110000240301", "110000241001", "400476889255", "400476889270", "110000243101", "400476889336", "110000019521", "110000244501", "400476889340", "400476889351", "400476889384", "400476889395", "400476889421", "110000248001", "110000021621", "110000022321", "110000023021", "400476889480", "110000249401", "110000108211", "400476889502", "110000250801", "400476889513", "400272037416", "400272037420", "400272037442", "110000111011", "110000027921", "110000254301", "400272037453", "110000255001", "400272037490", "400272037534", "400272037545", "110000030721", "110000255701", "110000115911", "110000032121", "110000257101", "400272037571", "400272037593", "400272037615", "400272037626", "400272037630", "110000034221", "910049582583", "910049582594", "910049582605", "910049582616", "910049582620", "910049582631", "400272037184", "110000125711", "400272037195", "110000036321", "910049582642", "910049582653", "910049582664", "910049582675", "400272037232", "400272037243", "400272037254", "910049582686", "400272037265", "910049582690", "910049582701", "400272037280", "400272037291", "910049582712", "400272037313", "910049582723", "910049582734", "910049582745", "910049582756", "910049582760", "110000262001", "910049582771", "400272037350", "910049582782", "110000262701", "910049582793", "400272037383", "400228258532", "110000045421", "110000046821", "400228258591", "110000263401", "400228258650", "110000049621", "400228258672", "110000051021", "400228258694", "110000053121", "400228258753", "110000265501", "110000053821", "400228258764", "110000266201", "110000054521", "400228258790", "110000055221", "400309010193", "400309010215", "400309010226", "400309010230", "110000057321", "110000269701", "110000150211", "400309010274", "110000059421", "110000152311", "110000271801", "110000060121", "400309010296", "110000061521", "110000062221", "400309010300", "400309010322", "110000062921", "400309010344", "400309010355", "400309010370", "400309010381", "110000064321", "400309010403", "110000274601", "110000065021", "400309010414", "400309010425", "110000158611", "400272037641", "110000066421", "400272037663", "400272037685", "110000278101", "400272037700", "400272037722", "400272037733", "400272037744", "110000069921", "110000070621", "110000280901", "110000072721", "400272037814", "110000074121", "400272037862", "110000074821", "400272037884", "110000283001", "400272037895", "110000076221", "400272038190", "110000076921", "400272038234", "400272038256", "400272038260", "110000080421", "110000176111", "110000286501", "400272038330", "110000081121", "110000287201", "400272038363", "400272038374", "110000288601", "110000083221", "110000289301", "110000183111", "110000084621", "110000085321", "400272037932", "110000086021", "400272037954", "400272038002", "110000292101", "110000088821", "400272038046", "400272038057", "110000292801", "400272038072", "400272038094", "110000293501", "110000194311", "400272038131", "110000197111", "110000091621", "110000092321", "110000093021", "110000199911", "110000295601", "400309017871", "110000094421", "400309017882", "400309017893", "110000095821", "400309017915", "400309017926", "110000096521", "110000205511", "110000097221", "400309017952", "110000097921", "400309017974", "400309018033", "110000101421", "400309018044", "400309018066", "910049582340", "910049582351", "910049582362", "400272038691", "110000300501", "910049582373", "910049582384", "110000214611", "910049582395", "910049582406", "910049582410", "400272038761", "110000301901", "910049582421", "910049582432", "910049582443", "110000216711", "400272038805", "110000217411", "400590221731", "910049582454", "400272038816", "110000304701", "400272038820", "910049582465", "910049582476", "910049582480", "910049582491", "910049582502", "110000218811", "110000306101", "910049582513", "400272038875", "910049582524", "910049582535", "910049582546", "910049582550", "910049582561", "400272038934", "910049582572", "400272038433", "110000224411", "110000116121", "110000225811", "400272038492", "400272038503", "400272038525", "400272038536", "110000227211", "110000118921", "400272038551", "110000228611", "400272038562", "400272038621", "400272038643", "110000123821", "110000125921", "110000314501", "110000234211", "400316478132", "110000235611", "400316478143", "110000315901", "110000237711", "400316478191", "110000316601", "110000238411", "110000239111", "400316478213", "110000239811", "110000317301", "110000135021", "110000240511", "400316478235", "400316478246", "110000242611", "400316478261", "110000243311", "400316478283", "400316478294", "400316478316", "400316478320", "110000320101", "400228258871", "110000141321", "400228258926", "110000322201", "110000247511", "110000249611", "400228259011", "400228259022", "110000251011", "400228259033", "400228259044", "110000324301", "110000252411", "400228259055", "400228259103", "400228259125", "400272039225", "110000254511", "110000326401", "400272039262", "400272039284", "400272039295", "400272039306", "400272039317", "110000255911", "110000256611", "400272039354", "110000330601", "110000258011", "110000258711", "110000163021", "110000333401", "400272039413", "400272039435", "400272038971", "110000262211", "400272039015", "400272039026", "400272039030", "110000262911", "400272039041", "110000338301", "110000263611", "400272039096", "110000339701", "400272039100", "110000265011", "400272039144", "110000265711", "110000341801", "400272039166", "110000342501", "400272039170", "110000178421", "400462465022", "400462465033", "400228259490", "400462465055", "110000344601", "400462465070", "400462465081", "400228259560", "400228259571", "400228259582", "400462465114", "400462465140", "400462465151", "400462465162", "110000272711", "400228259604", "400462465173", "400228259615", "400462465210", "110000190321", "400462465232", "400462465254", "400462465265", "400462465276", "400316476931", "110000353701", "110000354401", "400316476975", "110000276211", "400316476986", "110000355801", "110000278311", "400316477056", "110000356501", "110000279711", "400316477082", "400316477093", "400316477104", "110000357901", "110000281111", "110000013431", "400316477115", "910048800274", "400272039483", "910048800285", "910048800296", "910048800300", "400272039505", "110000282511", "110000358601", "400272039516", "910048800311", "110000209221", "400272039531", "910048800322", "910048800333", "110000283911", "400272039564", "110000359301", "910048800344", "910048800355", "910048800366", "110000360001", "910048800370", "910048800381", "110000360701", "910048800392", "910048800403", "910048800414", "910048800425", "910048800436", "910048800440", "110000018331", "400272039645", "910048800451", "910048800462", "110000019031", "910048800473", "910048800484", "910048800495", "910048800506", "400363295963", "400363295985", "400363296000", "110000023231", "400363296011", "110000287411", "400363296033", "110000024631", "400363296055", "110000288111", "400363296081", "110000025331", "110000366301", "400363296092", "110000026031", "400363296114", "110000226021", "400363296140", "110000367701", "400363296173", "110000228121", "400363296184", "110000368401", "110000028831", "400363297562", "400363297573", "110000228821", "400363297584", "400363297595", "400363297606", "400363297610", "400363297621", "400363297632", "400363297643", "400363297654", "110000230921", "110000030231", "400363297665", "110000231621", "400363297680", "110000030931", "400363297691", "400363297702", "400363297713", "400363297724", "110000295811", "400363297746", "400363297750", "400363297761", "110000235121", "110000374001", "110000033731", "110000235821", "110000236521", "400363297805", "400363297816", "400363297820", "400363297831", "400363297842", "400363297853", "110000237221", "110000375401", "400476549114", "110000238621", "110000035831", "400476549125", "400476549136", "400476549151", "400476549162", "400476549173", "400476549184", "400476549221", "110000378901", "110000037931", "110000379601", "400476549254", "110000243521", "400476549265", "110000039331", "110000244221", "110000244921", "110000300011", "110000381701", "110000040731", "110000300711", "110000246321", "400476549313", "110000247021", "110000384501", "110000248421", "110000042831", "110000304911", "110000043531", "110000387301", "110000306311", "110000044931", "110000045631", "110000388001", "110000307011", "110000250521", "110000307711", "110000389401", "110000308411", "110000049131", "110000390801", "110000049831", "110000253321", "110000391501", "110000254021", "110000392201", "110000392901", "110000254721", "110000255421", "110000313311", "110000393601", "110000394301", "110000256821", "110000314711", "110000054031", "110000315411", "110000258221", "110000316811", "110000397801", "400476880936", "110000258921", "110000057531", "400476880951", "400476880962", "400476880973", "400476881006", "400476881021", "110000261021", "400476881032", "110000060331", "110000319611", "110000399901", "110000261721", "400476881054", "110000400601", "110000262421", "400476881076", "110000321011", "400476881080", "400476881091", "400476881102", "400476881113", "400476881124", "400476881135", "110000065931", "110000322411", "110000402001", "400476881146", "110000067331", "110000068731", "110000266621", "110000326611", "110000069431", "110000327311", "110000267321", "110000070831", "110000406201", "110000406901", "110000071531", "110000072231", "110000329411", "110000268721", "110000270121", "110000072931", "910046771932", "110000270821", "910046771943", "910046771954", "910046771965", "910046771976", "910046771980", "110000074331", "910046771991", "110000015041", "910046772002", "110000271521", "910046772013", "910046772024", "910046772035", "910046772046", "110000015741", "910046772050", "910046772061", "910046772072", "110000077131", "910046772083", "910046772094", "910046772105", "110000412501", "110000077831", "910046772116", "110000334311", "910046772120", "910046772131", "910046772142", "110000414601", "110000273621", "910046772153", "110000274321", "110000018541", "110000415301", "110000079931", "110000019241", "110000275021", "110000335711", "110000081331", "110000275721", "110000416701", "110000336411", "110000417401", "110000022741", "110000082031", "110000278521", "110000083431", "110000337111", "110000023441", "110000084831", "110000282021", "110000420901", "110000086231", "400309011895", "400309011906", "110000283421", "110000086931", "400309011976", "110000341311", "400309012013", "400309012024", "110000425801", "110000426501", "110000342011", "110000284121", "110000090431", "110000284821", "110000029041", "400309012116", "110000029741", "110000286221", "110000286921", "400272039741", "110000030441", "400272039752", "110000287621", "400272039785", "400272039796", "110000430701", "400272039811", "110000289721", "400272039833", "110000096031", "110000431401", "400272039866", "110000096731", "400272039870", "110000349711", "400272039925", "110000432801", "400272039951", "110000291821", "110000100231", "110000033941", "400476886912", "110000433501", "110000352511", "110000293221", "110000103031", "400476886945", "400476886956", "400476886960", "110000104431", "110000036041", "110000293921", "400476887015", "400476887026", "110000434901", "400476887041", "110000435601", "400476887085", "110000356011", "400476887122", "400476887133", "400476887144", "110000040241", "110000356711", "400309025151", "110000296721", "400309025173", "110000041641", "110000437701", "400309025195", "400309025210", "110000111431", "400309025243", "400309025254", "110000359511", "400309025280", "400309025291", "110000439801", "110000299521", "400309025361", "400309025383", "400309025394", "910048697573", "910048697584", "910048697595", "910048697606", "400272040006", "110000362311", "400272040021", "910048697610", "910048697621", "910048697632", "910048697643", "110000442601", "910048697654", "910048697665", "910048697676", "110000121931", "400272040091", "910048697680", "400272040102", "110000048641", "910048697691");
    	List<String> tmp = Arrays.asList("400593834335", "400482160093", "400593830566", "400452577846", "400283303150", "400411323663", "400482072696", "400553089752", "400488054196", "400196440393", "400555264711", "400528475814", "400405452435", "400405452435");
    	//List<String> tmp = Arrays.asList("400593834335");
    	//List<String> tmp = Arrays.asList("910046772433", "910046772444", "110000014901", "910046772455", "910046772466", "110000017001", "110000017701", "110000018401", "910046772470", "110000019801", "910046772481", "910046772492", "910046772503", "910046772514", "110000023301", "910046772525", "910046772536", "110000025401", "910046772540", "910046772551", "910046772562", "910046772573", "110000028901", "110000029601", "110000030301", "910046772584", "910046772595", "910046772606", "910046772610", "910046772621", "910046772632", "910046772643", "910046772654", "910046772665", "110000038001", "110000038701", "110000039401", "110000040101", "110000040801", "110000043601", "110000044301", "110000045701", "110000046401", "110000047101", "110000050601", "110000053401", "110000054101", "110000055501", "110000056901", "110000057601", "110000058301", "110000059001", "110000061801", "110000062501", "110000063901", "110000064601", "110000066001", "110000070901", "110000071601", "110000072301", "110000073001", "110000075801", "110000076501", "110000077201", "110000080701", "110000081401", "110000082101", "110000083501", "110000084201", "110000085601", "110000086301", "110000087001", "110000087701", "110000088401", "110000089101", "110000089801", "110000091201", "110000091901", "110000093301", "110000096801", "400272036075", "110000101001", "400272036086", "110000103801", "400272036101", "110000106601", "110000107301", "400272036112", "400272036156", "400272036160", "400272036182", "400272036193", "400272036215", "400272036252", "400272036274", "110000013711", "110000122701", "400272036296", "400272036307", "400476875605");
    	
    	@SuppressWarnings({ "unchecked", "rawtypes" })
		List<String> result = (List)method.invoke(purgeIndividualDS, tmp);
    	//Assert.assertEquals(25, result.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Purge_YES() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	Customer customer = new Customer();
    	customer.setGin("999999999999");
    	ProvideCustomer360ViewResponse provide360ResponseMock = new ProvideCustomer360ViewResponse();
    	provide360ResponseMock.getCustomer().add(customer);
    	
    	Mockito.doReturn(provide360ResponseMock).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(0, ginsNotPurge.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Purge_FIDELIO() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	Customer customer = new Customer();
    	customer.setGin("999999999999");
    	CustomerExperienceResponse customerExperience = new CustomerExperienceResponse();
    	CustomerCaseData customerCaseData = new CustomerCaseData();
    	CustomerCase customerCase = new CustomerCase();
    	customerCase.setProviderId("FIDELIO");
    	customerCaseData.getCustomerCases().add(customerCase);
    	customerExperience.setCustomerCaseData(customerCaseData);
    	customer.setCustomerExperienceResponse(customerExperience);
    	
    	ProvideCustomer360ViewResponse provide360ResponseMock = new ProvideCustomer360ViewResponse();
    	provide360ResponseMock.getCustomer().add(customer);
    	
    	Mockito.doReturn(provide360ResponseMock).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(1, ginsNotPurge.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Purge_ICARE() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	Customer customer = new Customer();
    	customer.setGin("999999999999");
    	CustomerExperienceResponse customerExperience = new CustomerExperienceResponse();
    	CustomerCaseData customerCaseData = new CustomerCaseData();
    	CustomerCase customerCase = new CustomerCase();
    	customerCase.setProviderId("ICARE");
    	customerCaseData.getCustomerCases().add(customerCase);
    	customerExperience.setCustomerCaseData(customerCaseData);
    	customer.setCustomerExperienceResponse(customerExperience);
    	
    	ProvideCustomer360ViewResponse provide360ResponseMock = new ProvideCustomer360ViewResponse();
    	provide360ResponseMock.getCustomer().add(customer);
    	
    	Mockito.doReturn(provide360ResponseMock).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(1, ginsNotPurge.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Purge_PNR() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	Customer customer = new Customer();
    	customer.setGin("999999999999");
    	CustomerExperienceResponse customerExperience = new CustomerExperienceResponse();
    	PnrData pnrData = new PnrData();
    	Pnr pnr = new Pnr();
    	PnrId pnrId = new PnrId();
    	pnrId.setRecordLocator("M8XVNJ");
    	pnr.setPnrId(pnrId);
    	pnrData.getPnr().add(pnr);
    	customerExperience.setPnrData(pnrData);
    	customer.setCustomerExperienceResponse(customerExperience);
    	
    	ProvideCustomer360ViewResponse provide360ResponseMock = new ProvideCustomer360ViewResponse();
    	provide360ResponseMock.getCustomer().add(customer);
    	
    	Mockito.doReturn(provide360ResponseMock).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(1, ginsNotPurge.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Purge_Clickers_NO() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		Calendar limitDateClicker = Calendar.getInstance();
		limitDateClicker.setTime(new Date());
		limitDateClicker.add(Calendar.DAY_OF_YEAR, -1095);
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.set_limit_date_clickers(limitDateClicker);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	Customer customer = new Customer();
    	customer.setGin("999999999999");
    	CustomerProfileResponse customerProfileResponse = new CustomerProfileResponse();
    	CrmProfileData crmProfileData = new CrmProfileData();
    	ProfilingData profilingData = new ProfilingData();
    	profilingData.setKey("DAT_LST_CLK");
    	profilingData.setValue("2019-10-14T23:27:49.000Z");
    	crmProfileData.getCrmProfiling().add(profilingData);
    	customerProfileResponse.setCrmProfileData(crmProfileData);
    	customer.setCustomerProfileResponse(customerProfileResponse);

    	ProvideCustomer360ViewResponse provide360ResponseMock = new ProvideCustomer360ViewResponse();
    	provide360ResponseMock.getCustomer().add(customer);
    	
    	Mockito.doReturn(provide360ResponseMock).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(1, ginsNotPurge.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Purge_Clickers_NO_Multiple() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		Calendar limitDateClicker = Calendar.getInstance();
		limitDateClicker.setTime(new Date());
		limitDateClicker.add(Calendar.DAY_OF_YEAR, -1095);
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.set_limit_date_clickers(limitDateClicker);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	Customer customer = new Customer();
    	customer.setGin("999999999999");
    	CustomerProfileResponse customerProfileResponse = new CustomerProfileResponse();
    	CrmProfileData crmProfileData = new CrmProfileData();
    	ProfilingData profilingDataA = new ProfilingData();
    	profilingDataA.setKey("DAT_LST_CLK");
    	profilingDataA.setValue("2019-10-14T23:27:49.000Z");
    	crmProfileData.getCrmProfiling().add(profilingDataA);
    	ProfilingData profilingDataB = new ProfilingData();
    	profilingDataB.setKey("DAT_LST_CLK");
    	profilingDataB.setValue("2015-10-14T23:27:49.000Z");
    	crmProfileData.getCrmProfiling().add(profilingDataB);
    	customerProfileResponse.setCrmProfileData(crmProfileData);
    	customer.setCustomerProfileResponse(customerProfileResponse);

    	ProvideCustomer360ViewResponse provide360ResponseMock = new ProvideCustomer360ViewResponse();
    	provide360ResponseMock.getCustomer().add(customer);
    	
    	Mockito.doReturn(provide360ResponseMock).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(1, ginsNotPurge.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Purge_Clickers_YES() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	Customer customer = new Customer();
    	customer.setGin("999999999999");
    	CustomerProfileResponse customerProfileResponse = new CustomerProfileResponse();
    	CrmProfileData crmProfileData = new CrmProfileData();
    	ProfilingData profilingData = new ProfilingData();
    	profilingData.setKey("DAT_LST_CLK");
    	profilingData.setValue("2014-10-14T23:27:49.000Z");
    	crmProfileData.getCrmProfiling().add(profilingData);
    	customerProfileResponse.setCrmProfileData(crmProfileData);
    	customer.setCustomerProfileResponse(customerProfileResponse);

    	ProvideCustomer360ViewResponse provide360ResponseMock = new ProvideCustomer360ViewResponse();
    	provide360ResponseMock.getCustomer().add(customer);
    	
    	Mockito.doReturn(provide360ResponseMock).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(0, ginsNotPurge.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Purge_Clickers_YES_WITHOUT_CLICKERS() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	Customer customer = new Customer();
    	customer.setGin("999999999999");
    
    	ProvideCustomer360ViewResponse provide360ResponseMock = new ProvideCustomer360ViewResponse();
    	provide360ResponseMock.getCustomer().add(customer);
    	
    	Mockito.doReturn(provide360ResponseMock).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(0, ginsNotPurge.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Error_NotFound() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	ProvideCustomer360ViewBusinessFault provideCustomer360ViewBusinessFault = new ProvideCustomer360ViewBusinessFault();
    	provideCustomer360ViewBusinessFault.setErrorCode(ProvideCustomer360ViewBusinessError.NOT_FOUND);
    	ProvideCustomer360ViewBusinessException exception = new ProvideCustomer360ViewBusinessException("Error", provideCustomer360ViewBusinessFault);
    	Mockito.doThrow(exception).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(0, ginsNotPurge.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Error_IndvidualMerge() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	ProvideCustomer360ViewBusinessFault provideCustomer360ViewBusinessFault = new ProvideCustomer360ViewBusinessFault();
    	provideCustomer360ViewBusinessFault.setErrorCode(ProvideCustomer360ViewBusinessError.INDIVIDUAL_MERGED);
    	ProvideCustomer360ViewBusinessException exception = new ProvideCustomer360ViewBusinessException("Error", provideCustomer360ViewBusinessFault);
    	Mockito.doThrow(exception).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(0, ginsNotPurge.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Error_IndvidualCancelled() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	ProvideCustomer360ViewBusinessFault provideCustomer360ViewBusinessFault = new ProvideCustomer360ViewBusinessFault();
    	provideCustomer360ViewBusinessFault.setErrorCode(ProvideCustomer360ViewBusinessError.INDIVIDUAL_CANCELLED);
    	ProvideCustomer360ViewBusinessException exception = new ProvideCustomer360ViewBusinessException("Error", provideCustomer360ViewBusinessFault);
    	Mockito.doThrow(exception).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(0, ginsNotPurge.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Error_InvalidParameters() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	ProvideCustomer360ViewBusinessFault provideCustomer360ViewBusinessFault = new ProvideCustomer360ViewBusinessFault();
    	provideCustomer360ViewBusinessFault.setErrorCode(ProvideCustomer360ViewBusinessError.INVALID_PARAMETERS);
    	ProvideCustomer360ViewBusinessException exception = new ProvideCustomer360ViewBusinessException("Error", provideCustomer360ViewBusinessFault);
    	Mockito.doThrow(exception).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(1, ginsNotPurge.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Error_TechnicalError() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	ProvideCustomer360ViewBusinessFault provideCustomer360ViewBusinessFault = new ProvideCustomer360ViewBusinessFault();
    	provideCustomer360ViewBusinessFault.setErrorCode(ProvideCustomer360ViewBusinessError.TECHNICAL_ERROR);
    	ProvideCustomer360ViewBusinessException exception = new ProvideCustomer360ViewBusinessException("Error", provideCustomer360ViewBusinessFault);
    	Mockito.doThrow(exception).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(1, ginsNotPurge.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Error_Other() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	ProvideCustomer360ViewBusinessFault provideCustomer360ViewBusinessFault = new ProvideCustomer360ViewBusinessFault();
    	provideCustomer360ViewBusinessFault.setErrorCode(ProvideCustomer360ViewBusinessError.OTHER);
    	ProvideCustomer360ViewBusinessException exception = new ProvideCustomer360ViewBusinessException("Error", provideCustomer360ViewBusinessFault);
    	Mockito.doThrow(exception).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(1, ginsNotPurge.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testCallCBS_Error_MissingParameter() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ProvideCustomer360ViewBusinessException, SystemException {
		
		PurgeIndividualDS purgeIndividualDS = new PurgeIndividualDS();
    	purgeIndividualDS.set_period_in_days(1095);
    	purgeIndividualDS.set_max_records_cbs_report(0);
    	purgeIndividualDS.set_cpu_thread_cbs(4);
    	purgeIndividualDS.set_delay_in_ms(DEFAUTL_DELAY_CBS_360);
    	purgeIndividualDS.setContext(context);
    	Method method = PurgeIndividualDS.class.getDeclaredMethod("callToCBS", List.class);
    	method.setAccessible(true);
    	
    	ProvideCustomer360ViewBusinessFault provideCustomer360ViewBusinessFault = new ProvideCustomer360ViewBusinessFault();
    	provideCustomer360ViewBusinessFault.setErrorCode(ProvideCustomer360ViewBusinessError.MISSING_PARAMETERS);
    	ProvideCustomer360ViewBusinessException exception = new ProvideCustomer360ViewBusinessException("Error", provideCustomer360ViewBusinessFault);
    	Mockito.doThrow(exception).when(provideCustomer360ViewV3).provideCustomerView(Mockito.any(ProvideCustomer360ViewRequest.class));
    	
    	List<String> inputGins = new ArrayList<String>();
    	inputGins.add("999999999999");

		List<String> ginsNotPurge = (List)method.invoke(purgeIndividualDS, inputGins);
		
		Assert.assertEquals(1, ginsNotPurge.size());
	}
}
