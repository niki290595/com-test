#include "interfaces.h"
#include "iid.h"
#include <iostream>

int main() {
	CoInitialize(NULL); // This parameter is reserved,
						// and should be NULL
	HRESULT hr;
	IClassFactory* pCF = NULL;
	ICreateCar* pICreateCar = NULL;
	IStats* pStats = NULL;
	IEngine* pEngine = NULL;
	hr = CoGetClassObject(CLSID_CoCar, CLSCTX_INPROC_SERVER, NULL, IID_IClassFactory, (void**)&pCF);

	// Make a CoCar & get ICreateCar. 
	hr = pCF->CreateInstance(NULL, IID_ICreateCar, (void**)&pICreateCar);
	pCF->Release();

	if (SUCCEEDED(hr)) {
		pICreateCar->SetMaxSpeed(40); BSTR petName = SysAllocString(L"Mers!");
		pICreateCar->SetPetName(petName);
		SysFreeString(petName);

		// Now get IStats 
		hr = pICreateCar->QueryInterface(IID_IStats, (void**)&pStats);
		pICreateCar->Release();
	}

	if (SUCCEEDED(hr)) {
		// Show my car! 
		pStats->DisplayStats();
		hr = pStats->QueryInterface(IID_IEngine, (void**)&pEngine);
	}

	if (SUCCEEDED(hr)) {
		int curSp = 0;
		int maxSp = 0;
		pEngine->GetMaxSpeed(&maxSp);

		do { //zoom
			pEngine->SpeedUp();
			pEngine->GetCurSpeed(&curSp);
			std::cout << "Speed is: " << curSp << std::endl;
		} while (curSp <= maxSp);

		// Gotta convert to char array.
		char buff[80];
		BSTR bstr;
		pStats->GetPetName(&bstr);
		WideCharToMultiByte(CP_ACP, NULL, bstr, -1, buff, 80, NULL, NULL);
		std::cout << buff << " has blown up! Lead Foot!" << std::endl << std::endl;
		SysFreeString(bstr);

		if (pEngine) pEngine->Release();
		if (pStats) pStats->Release();
	}

	CoUninitialize();
	return 0;
}