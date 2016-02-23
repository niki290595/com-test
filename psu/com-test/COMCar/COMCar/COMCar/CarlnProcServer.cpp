#include <windows.h>
#include "CoCarClassFactory.cpp"

ULONG g_lockCount = 0; //кол-во блокировок сервера
ULONG g_objCount = 0; //кол-во живых объектов в сервере

STDAPI DllGetClassObject(REFCLSID rclsid, REFIID riid, void **ppv) {
	if (rclsid != CLSID_CoCar)
		return CLASS_E_CLASSNOTAVAILABLE;

	CoCarClassFactory *pCFact = new CoCarClassFactory;
	HRESULT hr = pCFact -> QueryInterface(riid, ppv);

	if (FAILED(hr))
		delete pCFact;
	return hr;
}

STDAPI DllCanUnloadNow() {
	return g_lockCount == 0 && g_objCount == 0 ? S_OK : S_FALSE;
}