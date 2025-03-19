import React, { useEffect } from 'react';
import type { PropsWithChildren } from 'react';
import {
  Button,
  ScrollView,
  StatusBar,
  useColorScheme,
  View,
} from 'react-native';

import {
  Colors
} from 'react-native/Libraries/NewAppScreen';

import { NativeModules, NativeEventEmitter } from 'react-native';
const { FirstModule, MyTalentsModule } = NativeModules
const eventEmitter = new NativeEventEmitter(FirstModule)
console.log(FirstModule)
FirstModule.createCounterEvent((res: string) => console.log(res + "plus"))


function App(): React.JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  useEffect(() => {
    eventEmitter.addListener('EventCount', count => {
      console.log(count)
    })

    return () => {
      eventEmitter.removeAllListeners('EventCount');
    }
  }, [])

  const promiseExample = async () => {
    try {
      const result = await FirstModule.createCounterPromise()
      console.log(result)
    } catch (error) {
      console.log(error)
    }
  }

  const openModule = () => {
    const credentials = {
      zeusId: '4b1bc2b6-7c97-4de8-a9d9-0f2f9cf519b2',
      password: 'GrupoSalinas2024'
    }
    const company = {
      companyId: '310',
      name: 'Grupo Nach',
      primaryColor: '#00C5FF'
    }
    const user = {
      zeusId: 'ba3e85c2-a82e-49b3-90d3-b2740eac2193',
      employeeNumber: '999956281',
      name: 'David Arturo',
      lastName: 'Martinez',
      secondLastName: 'Guzman',
      company: company,
      credentials: credentials
    }
    const userJson = JSON.stringify(user)
    MyTalentsModule.openModule(userJson)
  }

  const startServiceCoordinator = () => {
    MyTalentsModule.startServiceCoordinator()
  }

  return (
    <View style={backgroundStyle}>

      <ScrollView
        style={backgroundStyle}>
        <Button
          onPress={promiseExample}
          title="Run Javascript promise"
          color="green"
        />
        <View style={{ backgroundColor: 'blue', height: 30, width: 30 }} />
        <Button
          onPress={openModule}
          title="open module"
          color="blue"
        />
      </ScrollView>
    </View>
  );
}

export default App;
