import org.apache.camel.Exchange
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.test.junit4.CamelTestSupport
import org.junit.After
import org.junit.Before
import org.junit.Test

@Grab(group = 'org.apache.camel', module = 'camel-groovy', version = '2.3.0')
@Grab(group = 'org.apache.camel', module = 'camel-xmpp', version = '2.3.0')
@Grab(group = 'org.apache.camel', module = 'camel-core', version = '2.3.0')
@Grab(group = 'org.apache.camel', module = 'camel-test', version = '2.3.0')
@Grab(group = 'junit', module = 'junit', version = '4.4')
/**
 * For use by Castle Press LLC or anyone else
 * who gets their grubby little hands on it.
 *
 * User: steve
 * Date: Jun 8, 2010
 * Time: 2:46:18 PM
 */
class TransformerTest extends CamelTestSupport {

  @

  @Before
  void setup() {
  }

  @After
  void breakdown() {
  }

  @Override
  protected RouteBuilder createRouteBuilder() throws Exception {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("direct:start").to("mock:mymock")
      };
    }
  }

  @Test
  void testCopyFile() throws Exception {
    template.sendBodyAndHeader("file:///tmp/jabber", "READMENOW", Exchange.FILE_NAME, "READMENOW")
    Thread.sleep(1000)
    File target = new File("/tmp/jibber-jabber/READMENOW")
    assertTrue("File not copied", target.exists())
  }
}