import {Button, FormControl, InputGroup} from 'react-bootstrap'
import {BsCalendar, BsImage, BsSearch} from 'react-icons/bs'
import './search.scss'
import {forwardRef, useState} from 'react'
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.min.css'
import {useFindImagesQuery} from '../../redux/api/photoApi'
import {AiOutlineCloseCircle} from 'react-icons/all'

const Search = ({setSearchImageResults}) => {
  // Fetch results
  const [searchQuery, setSearchQuery] = useState({})
  const searchResult = useFindImagesQuery(searchQuery)

  // Search for specific image size
  const [currentImageSize, setCurrentImageSize] = useState(0)
  const imageSize = [{size: 1, value: 'normal'}, {size: 1.3, value: 'big'}, {size: 0.7, value: 'small'}]

  const changeImageSize = (e) => {
    setCurrentImageSize((currentImageSize + 1) % imageSize.length)

    e.currentTarget.style.fontSize = imageSize[currentImageSize].size + 'em'
  }

  // Search for specific date
  const [searchDate, setSearchDate] = useState([null, null])
  const [startDate, endDate] = searchDate
  const SearchElement = forwardRef(({value, onClick}, ref) => (
    <InputGroup.Text className="search-action" ref={ref}>
      <BsCalendar onClick={onClick}/>
      {value && <span className="search-date-value">{value}</span>}
      {value && <Button className="search-date-clear" onClick={() => setSearchDate([null, null])}
                        variant="link"><AiOutlineCloseCircle className="search-date-clear-icon"/></Button>}
    </InputGroup.Text>
  ))

  // Search for author or hashtag
  const changeSearchQuery = (e) => {
    let query = e.target.value
  }


  // Perform search
  const search = () => {
    let args = {}

    args.size = imageSize[currentImageSize].value

    setSearchQuery(Object.assign(args, searchQuery))
  }

  return (
    <div className="mx-3 my-2 flex-grow-1 search position-relative">
      <InputGroup>
        <FormControl id="search-input" placeholder="Type @ to search authors or # to search hashtags"
                     onChange={changeSearchQuery}/>
        <InputGroup.Text className="search-action" onClick={changeImageSize}><BsImage/></InputGroup.Text>
        <DatePicker selectsRange={true}
                    startDate={startDate}
                    endDate={endDate}
                    onChange={(date) => {
                      setSearchDate(date)
                    }}
                    showMonthDropdown
                    showYearDropdown
                    withPortal
                    customInput={<SearchElement/>}/>
        <InputGroup.Text className="search-action" onClick={search}><BsSearch/></InputGroup.Text>
      </InputGroup>
      {searchResult.isSuccess && searchResult.data.length > 0 &&
      <div className="position-absolute w-100 search-content">
        {searchResult.data.map((result) => <div>{result}</div>)}
      </div>}
    </div>
  )
}

export default Search