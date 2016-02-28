using System;
using System.Runtime.InteropServices;

namespace CarClientInCSharp
{
    [Guid("68B57DA1-999F-4768-8AA5-B15217F8E6EF")]
    public class ExplicitGuid { }

    public class NoExplicitGuid { }

    class Program
    {
        static void Main(string[] args) {
            Type explicitType = typeof(ExplicitGuid);
            Guid explicitGuid = explicitType.GUID;

            Type explicitCOM = Type.GetTypeFromCLSID(explicitGuid);
            Console.WriteLine("ExplicitType = {0};", explicitType.Name);
            Console.WriteLine("ExplicitGuid = {0};", explicitGuid);
            Console.WriteLine("ExplicitCOM = {0};", explicitCOM.Name);
            Console.WriteLine("Created {0} type from CLSID {1};\n", explicitCOM.Name, explicitGuid);

            Console.WriteLine("{0} and {1} equal: {2};\n",
                        explicitType.Name, explicitCOM.Name,
                        explicitType.Equals(explicitCOM));

            // Instantiate an ExplicitGuid object.
            try {
                object obj = Activator.CreateInstance(explicitCOM);
                Console.WriteLine("Instantiated {0} object = ", obj.GetType().Name);
                Console.WriteLine("Instantiated {0} object. Full name = ", obj.GetType().FullName);

                /*
                Object[] argArray = new Object[1];
                argArray[0] = 50;
                explicitCOM.InvokeMember("SetMaxSpeed", System.Reflection.BindingFlags.InvokeMethod, null, obj, argArray);*/
            } catch (COMException e) {
                Console.WriteLine("COM Exception:\n{0}\n", e.Message);
            }
        }
    }
}
