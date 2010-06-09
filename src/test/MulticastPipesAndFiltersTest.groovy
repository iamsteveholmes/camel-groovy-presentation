import org.apache.camel.test.junit4.CamelTestSupport
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.Exchange

import org.junit.Test
import org.junit.Before
import org.junit.After

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
class MulticastPipesAndFiltersTest extends CamelTestSupport {

  @Before
  void setup() {
    def readme = new File("/tmp/jabber/READMENOW")
    readme.withWriter {writer ->
      writer << "Read me now or pay the consequences!"
    }

    assert readme.exists()
  }

  @After
  void breakdown() {
    def readme = new File("/tmp/jibber-jabber/READMENOW")
    if (readme.exists())
      readme.delete()
  }

  @Override
  protected RouteBuilder createRouteBuilder() throws Exception {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {

        def props = new Properties()
        new File("/tmp/props.properties").withInputStream {
          stream -> props.load(stream)
        }
        from("file:///tmp/jabber").multicast().pipeline().to("file:///tmp/jibber-jabber").
                pipeline().to("xmpp://talk.google.com:5222/iamsteveholmes@gmail.com?serviceName=gmail.com&user=${props.username}&password=${props.password}")

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
