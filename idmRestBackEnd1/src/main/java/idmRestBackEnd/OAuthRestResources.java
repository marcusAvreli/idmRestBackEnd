package idmRestBackEnd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sailpoint.rest.oauth.SailPointOAuthRestApplication;


public class OAuthRestResources extends SailPointOAuthRestApplication {
	private static final Logger logger = LoggerFactory.getLogger(OAuthRestResources.class);
	public OAuthRestResources() {
		super();
		
		register(CORSFilter.class);
		register(MyOauthAPI.class);
		register(DataSourceEP.class);
		register(DbOjectEP.class);
		register(DbFunctionFieldEP.class);
		register(RprtColumnEP.class);
		register(RprtUserViewEP.class);		
		register(RprtUserViewColumnEP.class);
		register(RprtMetaEP.class);
		register(OAuthTokenResourceWrapper.class);
		register(ValidateToken.class);
		
		
	}

}