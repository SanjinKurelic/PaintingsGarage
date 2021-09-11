import Header from './components/header/Header'
import ImageGallery from './components/image/ImageGallery'
import {Container} from 'react-bootstrap'
import {useState} from 'react'
import Image from './components/image/Image'
import {useGetLatestImagesQuery} from './redux/api/searchApi'
import Footer from './components/footer/Footer'
import {BrowserRouter, Route} from 'react-router-dom'
import Login from './components/login/Login'

function App() {
  const latestImages = useGetLatestImagesQuery()
  const [selectedImage, setSelectedImage] = useState(null)

  return (
    <BrowserRouter>
      <Header/>
      <Container>
        <Route path="/" exact render={() => (
          <>
            {latestImages.isSuccess && <ImageGallery images={latestImages.data} setSelectedImage={setSelectedImage}/>}
            {selectedImage && <Image image={selectedImage} setSelectedImage={setSelectedImage}/>}
          </>
        )}/>
        <Route path="/login" component={Login}/>
      </Container>
      <Footer/>
    </BrowserRouter>
  )
}

export default App
