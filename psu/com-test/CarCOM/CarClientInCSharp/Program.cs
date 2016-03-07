using System;
using System.Runtime.InteropServices;


namespace CarClientInCSharp
{
    [ComImport, Guid("6BEBBF0C-618F-4E48-91BC-D4B99761CA50")]
    public class IID_IEngine { }

    [ComImport, Guid("C5457176-C785-449F-803D-16352C64C230")]
    public class IID_IStats { }

    [ComImport, Guid("8A26D393-0AC1-48F1-8B81-851EB59BDFBA")]
    public class IID_ICreateCar { }
    
    [ComImport, Guid("68B57DA1-999F-4768-8AA5-B15217F8E6EF")]
    public class CLSID_CoCar { }

    //IStats используется для получения имени автомобиля, а также
    //для отображения параметров состояния реализуемого объекта
    [Guid("C5457176-C785-449F-803D-16352C64C230"),
        InterfaceType(ComInterfaceType.InterfaceIsIUnknown)]
    interface IStats {
        void DisplayStats();
        void GetPetName([Out, MarshalAs(UnmanagedType.BStr)] out string petName);
    };

    //IEngine моделирует поведение двигателя. Можем ускорять его,
    //получать значения максимальной и текущей скорости
    [Guid("6BEBBF0C-618F-4E48-91BC-D4B99761CA50"),
        InterfaceType(ComInterfaceType.InterfaceIsIUnknown)]
    interface IEngine {
        void SpeedUp();
        void GetMaxSpeed([Out] out int maxSpeed);
        void GetCurSpeed([Out] out int curSpeed);
    };

    //ICreateCar используется для присвоения имени реализуемому
    //объекту и задания максимальной скорости
    [Guid("8A26D393-0AC1-48F1-8B81-851EB59BDFBA"),
        InterfaceType(ComInterfaceType.InterfaceIsIUnknown)]
    interface ICreateCar {
        void SetPetName([In, MarshalAs(UnmanagedType.BStr)] string petName);
        void SetMaxSpeed([In] int maxSpeed);
    };

    class Program {
        [STAThread] static void Main(string[] args) {
            // Instantiate an ExplicitGuid object.
            try {
                object obj = Activator.CreateInstance(Type.GetTypeFromCLSID(typeof(CLSID_CoCar).GUID));
                ((ICreateCar)obj).SetMaxSpeed(40);
                ((ICreateCar)obj).SetPetName("!Mers");
                ((IStats)obj).DisplayStats();

                int curSp = 0;
                int maxSp = 0;
                ((IEngine)obj).GetMaxSpeed(out maxSp);
                do {
                    ((IEngine)obj).SpeedUp();
                    ((IEngine)obj).GetCurSpeed(out curSp);
                    Console.WriteLine("Speed is {0}", curSp);
                } while (curSp <= maxSp);
                string name = ""; 
                ((IStats)obj).GetPetName(out name);
                Console.WriteLine("{0} has blown up! Lead Foot!", name);

            } catch (COMException e) {
                Console.WriteLine("COM Exception:\n{0}\n", e.Message);
            }
        }
    }
}
