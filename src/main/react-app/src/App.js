import {Button, Container} from 'react-bootstrap'
import Header from './components/header/Header'
import ImageGallery from './components/image/ImageGallery'
import {useState} from 'react'
import Image from './components/image/Image'
import {useGetLatestImagesQuery} from './redux/api/photoApi'
import Footer from './components/footer/Footer'
import {BrowserRouter, Route} from 'react-router-dom'
import Login from './components/login/Login'
import User from './components/user/User'

function App() {
  const latestImages = useGetLatestImagesQuery()
  const [selectedImage, setSelectedImage] = useState(null)
  const [searchImageResults, setSearchImageResults] = useState(null)
  const [searchFired, setSearchFired] = useState(false)

  const images = () => {
    if (searchFired && searchImageResults.isSuccess) {
      return searchImageResults.data
    }
    if (!searchFired && latestImages.isSuccess) {
      return latestImages.data
    }
    return []
  }

  return (
    <BrowserRouter>
      <Header setSearchImageResults={setSearchImageResults} setSearchFired={setSearchFired}/>
      <Container style={{marginBottom: '58px'}}>
        <Route path="/" exact render={() => (
          <>
            {(images().length === 0 && searchFired) && <div className="m-3 fst-italic text-danger">
              Sorry, but nothing matched your search terms. Please try again with some different keywords or&nbsp;
              <Button variant="link" className="fst-italic p-0 align-baseline" onClick={() => setSearchFired(false)}>view
                latest images.</Button>
            </div>}
            <ImageGallery images={images()} setSelectedImage={setSelectedImage}/>
            {selectedImage && <Image image={selectedImage} setSelectedImage={setSelectedImage}/>}
          </>
        )}/>
        <Route path="/login" component={Login}/>
        <Route path="/user" component={User}/>
      </Container>
      <Footer/>
    </BrowserRouter>
  )
}

export default App
