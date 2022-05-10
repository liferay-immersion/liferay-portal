import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import org.osgi.framework.BundleContext;
import com.liferay.portal.security.ldap.exportimport.LDAPUserImporter;

BundleContext bundleContext = SystemBundleUtil.getBundleContext();
def ldapUserImporter = bundleContext.getService(bundleContext.getServiceReference(LDAPUserImporter.class));

ldapUserImporter.importUsers();